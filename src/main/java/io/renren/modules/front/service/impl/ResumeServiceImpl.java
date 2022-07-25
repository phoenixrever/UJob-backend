package io.renren.modules.front.service.impl;

import io.renren.common.exception.RRException;
import io.renren.modules.front.entity.CertificateEntity;
import io.renren.modules.front.entity.GeneralUserEntity;
import io.renren.modules.front.service.CertificateService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.front.dao.ResumeDao;
import io.renren.modules.front.entity.ResumeEntity;
import io.renren.modules.front.service.ResumeService;
import org.springframework.transaction.annotation.Transactional;


@Service("resumeService")
public class ResumeServiceImpl extends ServiceImpl<ResumeDao, ResumeEntity> implements ResumeService {

    @Autowired
    private CertificateService certificateService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ResumeEntity> page = this.page(
                new Query<ResumeEntity>().getPage(params),
                new QueryWrapper<ResumeEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional (rollbackFor = Exception.class)
    @Override
    public void saveWithCertificate(ResumeEntity resume) {
        GeneralUserEntity user = (GeneralUserEntity) SecurityUtils.getSubject().getPrincipal();
        resume.setUserId(user.getUserId());
        boolean save = this.save(resume);
        if (!save) {
           throw new RRException("保存简历失败");
        }
        List<CertificateEntity> certificateEntities = resume.getCertificateEntities();
        if (certificateEntities.size() > 0) {
            certificateEntities.forEach(certificate -> {
                certificate.setResumeId(resume.getId());
                boolean saveResult = certificateService.save(certificate);
                if (!saveResult) {
                    throw new RRException("保存证书失败");
                }
            });
        }
    }

    @Override
    public ResumeEntity getWithCertificateById(Long userId) {
        ResumeEntity resumeEntity = this.query().eq("user_id", userId).one();

        ArrayList<CertificateEntity> certificateEntities = new ArrayList<>();
        certificateService.query().eq("resume_id", resumeEntity.getId()).list().forEach(certificate -> {
            certificateEntities.add(certificate);
        });
        resumeEntity.setCertificateEntities(certificateEntities);
        return resumeEntity;
    }

    @Transactional (rollbackFor = Exception.class)
    @Override
    public void updateWithCertificate(ResumeEntity resume) {
        boolean b = this.updateById(resume);
        if (!b) {
            throw new RRException("修改简历失败");
        }
        //修改证书 先删除原来的证书，再添加新的证书 update的话太麻烦
        List<CertificateEntity> certificateEntities = certificateService.query().eq("resume_id", resume.getId()).list();
        if (certificateEntities.size() > 0) {
            boolean removeByIds = certificateService.removeByIds(certificateEntities.stream().map(CertificateEntity::getId).collect(Collectors.toList()));
            if (!removeByIds) {
                throw new RRException("删除证书失败");
            }
        }

        List<CertificateEntity> entityList = resume.getCertificateEntities();
        if (entityList.size() > 0) {
            entityList.forEach(certificate -> {
                certificate.setResumeId(resume.getId());
                //保存id在的话有没有影响忘记了 有空查一下 反正null是没错的
                certificate.setId(null);
                boolean saveResult = certificateService.save(certificate);
                if (!saveResult) {
                    throw new RRException("保存证书失败");
                }
            });
        }
    }
}