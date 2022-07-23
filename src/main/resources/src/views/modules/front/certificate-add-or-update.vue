<template>
  <el-dialog
    :title="!dataForm.uuid ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="id" prop="id">
      <el-input v-model="dataForm.id" placeholder="id"></el-input>
    </el-form-item>
    <el-form-item label="资格证书名称" prop="name">
      <el-input v-model="dataForm.name" placeholder="资格证书名称"></el-input>
    </el-form-item>
    <el-form-item label="取得日期" prop="date">
      <el-input v-model="dataForm.date" placeholder="取得日期"></el-input>
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
          id: '',
          uuid: 0,
          name: '',
          date: ''
        },
        dataRule: {
          id: [
            { required: true, message: 'id不能为空', trigger: 'blur' }
          ],
          name: [
            { required: true, message: '资格证书名称不能为空', trigger: 'blur' }
          ],
          date: [
            { required: true, message: '取得日期不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.uuid = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.uuid) {
            this.$http({
              url: this.$http.adornUrl(`/front/certificate/info/${this.dataForm.uuid}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.id = data.certificate.id
                this.dataForm.name = data.certificate.name
                this.dataForm.date = data.certificate.date
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
              url: this.$http.adornUrl(`/front/certificate/${!this.dataForm.uuid ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id,
                'uuid': this.dataForm.uuid || undefined,
                'name': this.dataForm.name,
                'date': this.dataForm.date
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
