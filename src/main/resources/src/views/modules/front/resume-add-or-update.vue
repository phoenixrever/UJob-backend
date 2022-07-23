<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="姓名" prop="name">
      <el-input v-model="dataForm.name" placeholder="姓名"></el-input>
    </el-form-item>
    <el-form-item label="平假名姓名" prop="hiraganaName">
      <el-input v-model="dataForm.hiraganaName" placeholder="平假名姓名"></el-input>
    </el-form-item>
    <el-form-item label="性别" prop="gender">
      <el-input v-model="dataForm.gender" placeholder="性别"></el-input>
    </el-form-item>
    <el-form-item label="生日" prop="birthday">
      <el-input v-model="dataForm.birthday" placeholder="生日"></el-input>
    </el-form-item>
    <el-form-item label="电话号码" prop="phone">
      <el-input v-model="dataForm.phone" placeholder="电话号码"></el-input>
    </el-form-item>
    <el-form-item label="微信号" prop="wechat">
      <el-input v-model="dataForm.wechat" placeholder="微信号"></el-input>
    </el-form-item>
    <el-form-item label="邮箱" prop="email">
      <el-input v-model="dataForm.email" placeholder="邮箱"></el-input>
    </el-form-item>
    <el-form-item label="市" prop="city">
      <el-input v-model="dataForm.city" placeholder="市"></el-input>
    </el-form-item>
    <el-form-item label="区" prop="area">
      <el-input v-model="dataForm.area" placeholder="区"></el-input>
    </el-form-item>
    <el-form-item label="详细地址" prop="areaDetail">
      <el-input v-model="dataForm.areaDetail" placeholder="详细地址"></el-input>
    </el-form-item>
    <el-form-item label="线路" prop="line">
      <el-input v-model="dataForm.line" placeholder="线路"></el-input>
    </el-form-item>
    <el-form-item label="駅" prop="station">
      <el-input v-model="dataForm.station" placeholder="駅"></el-input>
    </el-form-item>
    <el-form-item label="搬家预定" prop="moveHouse">
      <el-input v-model="dataForm.moveHouse" placeholder="搬家预定"></el-input>
    </el-form-item>
    <el-form-item label="再留资格" prop="visa">
      <el-input v-model="dataForm.visa" placeholder="再留资格"></el-input>
    </el-form-item>
    <el-form-item label="来日年数" prop="year">
      <el-input v-model="dataForm.year" placeholder="来日年数"></el-input>
    </el-form-item>
    <el-form-item label="配偶" prop="spouse">
      <el-input v-model="dataForm.spouse" placeholder="配偶"></el-input>
    </el-form-item>
    <el-form-item label="是否有家族 1 有 0 无" prop="family">
      <el-input v-model="dataForm.family" placeholder="是否有家族 1 有 0 无"></el-input>
    </el-form-item>
    <el-form-item label="日语水平" prop="japaneseLevel">
      <el-input v-model="dataForm.japaneseLevel" placeholder="日语水平"></el-input>
    </el-form-item>
    <el-form-item label="日本語能力試験" prop="jlpt">
      <el-input v-model="dataForm.jlpt" placeholder="日本語能力試験"></el-input>
    </el-form-item>
    <el-form-item label="最终学历" prop="education">
      <el-input v-model="dataForm.education" placeholder="最终学历"></el-input>
    </el-form-item>
    <el-form-item label="本籍地" prop="hometown">
      <el-input v-model="dataForm.hometown" placeholder="本籍地"></el-input>
    </el-form-item>
    <el-form-item label="ステイタス" prop="status">
      <el-input v-model="dataForm.status" placeholder="ステイタス"></el-input>
    </el-form-item>
    <el-form-item label="学校名" prop="schoolName">
      <el-input v-model="dataForm.schoolName" placeholder="学校名"></el-input>
    </el-form-item>
    <el-form-item label="入学年度" prop="admissionDate">
      <el-input v-model="dataForm.admissionDate" placeholder="入学年度"></el-input>
    </el-form-item>
    <el-form-item label="卒業年度" prop="graduationDate">
      <el-input v-model="dataForm.graduationDate" placeholder="卒業年度"></el-input>
    </el-form-item>
    <el-form-item label="専攻" prop="major">
      <el-input v-model="dataForm.major" placeholder="専攻"></el-input>
    </el-form-item>
    <el-form-item label="会社名" prop="companyName">
      <el-input v-model="dataForm.companyName" placeholder="会社名"></el-input>
    </el-form-item>
    <el-form-item label="入社日" prop="hiredDate">
      <el-input v-model="dataForm.hiredDate" placeholder="入社日"></el-input>
    </el-form-item>
    <el-form-item label="退社日" prop="leaveDate">
      <el-input v-model="dataForm.leaveDate" placeholder="退社日"></el-input>
    </el-form-item>
    <el-form-item label="資格免许id 逗号分离" prop="certificateIds">
      <el-input v-model="dataForm.certificateIds" placeholder="資格免许id 逗号分离"></el-input>
    </el-form-item>
    <el-form-item label="興味" prop="hobby">
      <el-input v-model="dataForm.hobby" placeholder="興味"></el-input>
    </el-form-item>
    <el-form-item label="特技" prop="skill">
      <el-input v-model="dataForm.skill" placeholder="特技"></el-input>
    </el-form-item>
    <el-form-item label="自己紹介" prop="selfintro">
      <el-input v-model="dataForm.selfintro" placeholder="自己紹介"></el-input>
    </el-form-item>
    <el-form-item label="志望動機" prop="motivation">
      <el-input v-model="dataForm.motivation" placeholder="志望動機"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          id: 0,
          name: '',
          hiraganaName: '',
          gender: '',
          birthday: '',
          phone: '',
          wechat: '',
          email: '',
          city: '',
          area: '',
          areaDetail: '',
          line: '',
          station: '',
          moveHouse: '',
          visa: '',
          year: '',
          spouse: '',
          family: '',
          japaneseLevel: '',
          jlpt: '',
          education: '',
          hometown: '',
          status: '',
          schoolName: '',
          admissionDate: '',
          graduationDate: '',
          major: '',
          companyName: '',
          hiredDate: '',
          leaveDate: '',
          certificateIds: '',
          hobby: '',
          skill: '',
          selfintro: '',
          motivation: ''
        },
        dataRule: {
          name: [
            { required: true, message: '姓名不能为空', trigger: 'blur' }
          ],
          hiraganaName: [
            { required: true, message: '平假名姓名不能为空', trigger: 'blur' }
          ],
          gender: [
            { required: true, message: '性别不能为空', trigger: 'blur' }
          ],
          birthday: [
            { required: true, message: '生日不能为空', trigger: 'blur' }
          ],
          phone: [
            { required: true, message: '电话号码不能为空', trigger: 'blur' }
          ],
          wechat: [
            { required: true, message: '微信号不能为空', trigger: 'blur' }
          ],
          email: [
            { required: true, message: '邮箱不能为空', trigger: 'blur' }
          ],
          city: [
            { required: true, message: '市不能为空', trigger: 'blur' }
          ],
          area: [
            { required: true, message: '区不能为空', trigger: 'blur' }
          ],
          areaDetail: [
            { required: true, message: '详细地址不能为空', trigger: 'blur' }
          ],
          line: [
            { required: true, message: '线路不能为空', trigger: 'blur' }
          ],
          station: [
            { required: true, message: '駅不能为空', trigger: 'blur' }
          ],
          moveHouse: [
            { required: true, message: '搬家预定不能为空', trigger: 'blur' }
          ],
          visa: [
            { required: true, message: '再留资格不能为空', trigger: 'blur' }
          ],
          year: [
            { required: true, message: '来日年数不能为空', trigger: 'blur' }
          ],
          spouse: [
            { required: true, message: '配偶不能为空', trigger: 'blur' }
          ],
          family: [
            { required: true, message: '是否有家族 1 有 0 无不能为空', trigger: 'blur' }
          ],
          japaneseLevel: [
            { required: true, message: '日语水平不能为空', trigger: 'blur' }
          ],
          jlpt: [
            { required: true, message: '日本語能力試験不能为空', trigger: 'blur' }
          ],
          education: [
            { required: true, message: '最终学历不能为空', trigger: 'blur' }
          ],
          hometown: [
            { required: true, message: '本籍地不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: 'ステイタス不能为空', trigger: 'blur' }
          ],
          schoolName: [
            { required: true, message: '学校名不能为空', trigger: 'blur' }
          ],
          admissionDate: [
            { required: true, message: '入学年度不能为空', trigger: 'blur' }
          ],
          graduationDate: [
            { required: true, message: '卒業年度不能为空', trigger: 'blur' }
          ],
          major: [
            { required: true, message: '専攻不能为空', trigger: 'blur' }
          ],
          companyName: [
            { required: true, message: '会社名不能为空', trigger: 'blur' }
          ],
          hiredDate: [
            { required: true, message: '入社日不能为空', trigger: 'blur' }
          ],
          leaveDate: [
            { required: true, message: '退社日不能为空', trigger: 'blur' }
          ],
          certificateIds: [
            { required: true, message: '資格免许id 逗号分离不能为空', trigger: 'blur' }
          ],
          hobby: [
            { required: true, message: '興味不能为空', trigger: 'blur' }
          ],
          skill: [
            { required: true, message: '特技不能为空', trigger: 'blur' }
          ],
          selfintro: [
            { required: true, message: '自己紹介不能为空', trigger: 'blur' }
          ],
          motivation: [
            { required: true, message: '志望動機不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/front/resume/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.name = data.resume.name
                this.dataForm.hiraganaName = data.resume.hiraganaName
                this.dataForm.gender = data.resume.gender
                this.dataForm.birthday = data.resume.birthday
                this.dataForm.phone = data.resume.phone
                this.dataForm.wechat = data.resume.wechat
                this.dataForm.email = data.resume.email
                this.dataForm.city = data.resume.city
                this.dataForm.area = data.resume.area
                this.dataForm.areaDetail = data.resume.areaDetail
                this.dataForm.line = data.resume.line
                this.dataForm.station = data.resume.station
                this.dataForm.moveHouse = data.resume.moveHouse
                this.dataForm.visa = data.resume.visa
                this.dataForm.year = data.resume.year
                this.dataForm.spouse = data.resume.spouse
                this.dataForm.family = data.resume.family
                this.dataForm.japaneseLevel = data.resume.japaneseLevel
                this.dataForm.jlpt = data.resume.jlpt
                this.dataForm.education = data.resume.education
                this.dataForm.hometown = data.resume.hometown
                this.dataForm.status = data.resume.status
                this.dataForm.schoolName = data.resume.schoolName
                this.dataForm.admissionDate = data.resume.admissionDate
                this.dataForm.graduationDate = data.resume.graduationDate
                this.dataForm.major = data.resume.major
                this.dataForm.companyName = data.resume.companyName
                this.dataForm.hiredDate = data.resume.hiredDate
                this.dataForm.leaveDate = data.resume.leaveDate
                this.dataForm.certificateIds = data.resume.certificateIds
                this.dataForm.hobby = data.resume.hobby
                this.dataForm.skill = data.resume.skill
                this.dataForm.selfintro = data.resume.selfintro
                this.dataForm.motivation = data.resume.motivation
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/front/resume/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'name': this.dataForm.name,
                'hiraganaName': this.dataForm.hiraganaName,
                'gender': this.dataForm.gender,
                'birthday': this.dataForm.birthday,
                'phone': this.dataForm.phone,
                'wechat': this.dataForm.wechat,
                'email': this.dataForm.email,
                'city': this.dataForm.city,
                'area': this.dataForm.area,
                'areaDetail': this.dataForm.areaDetail,
                'line': this.dataForm.line,
                'station': this.dataForm.station,
                'moveHouse': this.dataForm.moveHouse,
                'visa': this.dataForm.visa,
                'year': this.dataForm.year,
                'spouse': this.dataForm.spouse,
                'family': this.dataForm.family,
                'japaneseLevel': this.dataForm.japaneseLevel,
                'jlpt': this.dataForm.jlpt,
                'education': this.dataForm.education,
                'hometown': this.dataForm.hometown,
                'status': this.dataForm.status,
                'schoolName': this.dataForm.schoolName,
                'admissionDate': this.dataForm.admissionDate,
                'graduationDate': this.dataForm.graduationDate,
                'major': this.dataForm.major,
                'companyName': this.dataForm.companyName,
                'hiredDate': this.dataForm.hiredDate,
                'leaveDate': this.dataForm.leaveDate,
                'certificateIds': this.dataForm.certificateIds,
                'hobby': this.dataForm.hobby,
                'skill': this.dataForm.skill,
                'selfintro': this.dataForm.selfintro,
                'motivation': this.dataForm.motivation
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
