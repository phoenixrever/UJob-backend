<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="案件名称" prop="name">
      <el-input v-model="dataForm.name" placeholder="案件名称"></el-input>
    </el-form-item>
    <el-form-item label="业务分野" prop="menu">
      <el-input v-model="dataForm.menu" placeholder="业务分野"></el-input>
    </el-form-item>
    <el-form-item label="工作经验" prop="experience">
      <el-input v-model="dataForm.experience" placeholder="工作经验"></el-input>
    </el-form-item>
    <el-form-item label="工作地点" prop="area">
      <el-input v-model="dataForm.area" placeholder="工作地点"></el-input>
    </el-form-item>
    <el-form-item label="日语能力" prop="japanese">
      <el-input v-model="dataForm.japanese" placeholder="日语能力"></el-input>
    </el-form-item>
    <el-form-item label="中国语对应 0 否 1 是" prop="chinese">
      <el-input v-model="dataForm.chinese" placeholder="中国语对应 0 否 1 是"></el-input>
    </el-form-item>
    <el-form-item label="详细地址" prop="address">
      <el-input v-model="dataForm.address" placeholder="详细地址"></el-input>
    </el-form-item>
    <el-form-item label="电话号码" prop="phone">
      <el-input v-model="dataForm.phone" placeholder="电话号码"></el-input>
    </el-form-item>
    <el-form-item label="最近车站" prop="station">
      <el-input v-model="dataForm.station" placeholder="最近车站"></el-input>
    </el-form-item>
    <el-form-item label="薪水" prop="salary">
      <el-input v-model="dataForm.salary" placeholder="薪水"></el-input>
    </el-form-item>
    <el-form-item label="交通方式" prop="distance">
      <el-input v-model="dataForm.distance" placeholder="交通方式"></el-input>
    </el-form-item>
    <el-form-item label="案例详情 待定 会改" prop="detail">
      <el-input v-model="dataForm.detail" placeholder="案例详情 待定 会改"></el-input>
    </el-form-item>
    <el-form-item label="" prop="startDate">
      <el-input v-model="dataForm.startDate" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="endDate">
      <el-input v-model="dataForm.endDate" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="createdTime">
      <el-input v-model="dataForm.createdTime" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="updatedTime">
      <el-input v-model="dataForm.updatedTime" placeholder=""></el-input>
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
          menu: '',
          experience: '',
          area: '',
          japanese: '',
          chinese: '',
          address: '',
          phone: '',
          station: '',
          salary: '',
          distance: '',
          detail: '',
          startDate: '',
          endDate: '',
          createdTime: '',
          updatedTime: ''
        },
        dataRule: {
          name: [
            { required: true, message: '案件名称不能为空', trigger: 'blur' }
          ],
          menu: [
            { required: true, message: '业务分野不能为空', trigger: 'blur' }
          ],
          experience: [
            { required: true, message: '工作经验不能为空', trigger: 'blur' }
          ],
          area: [
            { required: true, message: '工作地点不能为空', trigger: 'blur' }
          ],
          japanese: [
            { required: true, message: '日语能力不能为空', trigger: 'blur' }
          ],
          chinese: [
            { required: true, message: '中国语对应 0 否 1 是不能为空', trigger: 'blur' }
          ],
          address: [
            { required: true, message: '详细地址不能为空', trigger: 'blur' }
          ],
          phone: [
            { required: true, message: '电话号码不能为空', trigger: 'blur' }
          ],
          station: [
            { required: true, message: '最近车站不能为空', trigger: 'blur' }
          ],
          salary: [
            { required: true, message: '薪水不能为空', trigger: 'blur' }
          ],
          distance: [
            { required: true, message: '交通方式不能为空', trigger: 'blur' }
          ],
          detail: [
            { required: true, message: '案例详情 待定 会改不能为空', trigger: 'blur' }
          ],
          startDate: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          endDate: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          createdTime: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          updatedTime: [
            { required: true, message: '不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/front/case/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.name = data.case.name
                this.dataForm.menu = data.case.menu
                this.dataForm.experience = data.case.experience
                this.dataForm.area = data.case.area
                this.dataForm.japanese = data.case.japanese
                this.dataForm.chinese = data.case.chinese
                this.dataForm.address = data.case.address
                this.dataForm.phone = data.case.phone
                this.dataForm.station = data.case.station
                this.dataForm.salary = data.case.salary
                this.dataForm.distance = data.case.distance
                this.dataForm.detail = data.case.detail
                this.dataForm.startDate = data.case.startDate
                this.dataForm.endDate = data.case.endDate
                this.dataForm.createdTime = data.case.createdTime
                this.dataForm.updatedTime = data.case.updatedTime
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
              url: this.$http.adornUrl(`/front/case/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'name': this.dataForm.name,
                'menu': this.dataForm.menu,
                'experience': this.dataForm.experience,
                'area': this.dataForm.area,
                'japanese': this.dataForm.japanese,
                'chinese': this.dataForm.chinese,
                'address': this.dataForm.address,
                'phone': this.dataForm.phone,
                'station': this.dataForm.station,
                'salary': this.dataForm.salary,
                'distance': this.dataForm.distance,
                'detail': this.dataForm.detail,
                'startDate': this.dataForm.startDate,
                'endDate': this.dataForm.endDate,
                'createdTime': this.dataForm.createdTime,
                'updatedTime': this.dataForm.updatedTime
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
