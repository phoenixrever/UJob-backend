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
    @Autowired
    private GeneralUserServiceImpl generalUserService;

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

         //save 出错会自动抛出错误触发回滚 不需要捕获 需要自定义错误  可以捕获
         this.save(resume);
        List<CertificateEntity> certificateEntities = resume.getCertificateEntities();
        if (certificateEntities.size() > 0) {
            certificateEntities.forEach(certificate -> {
                certificate.setResumeId(resume.getId());
                 certificateService.save(certificate);
            });
        }
        //user 里面保存resumeId 的冗余字段 暂时保留以后再研究
        GeneralUserEntity generalUser = new GeneralUserEntity();
        generalUser.setUserId(user.getUserId());
        generalUser.setResumeId(resume.getId());
        generalUserService.updateById(generalUser);
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
        this.updateById(resume);
        //修改证书 先删除原来的证书，再添加新的证书 update的话太麻烦
        List<CertificateEntity> certificateEntities = certificateService.query().eq("resume_id", resume.getId()).list();
        if (certificateEntities.size() > 0) {
           certificateService.removeByIds(certificateEntities.stream().map(CertificateEntity::getId).collect(Collectors.toList()));
        }

        List<CertificateEntity> entityList = resume.getCertificateEntities();
        if (entityList.size() > 0) {
            entityList.forEach(certificate -> {
                certificate.setResumeId(resume.getId());
                //保存id在的话有没有影响忘记了 有空查一下 反正null是没错的
                certificate.setId(null);
                certificateService.save(certificate);
            });
        }
    }
}