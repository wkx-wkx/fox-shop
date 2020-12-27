<template>
  <div class="app-container">
    <el-card class="filter-container" shadow="never">
      <div>
        <i class="el-icon-search"></i>
        <span>筛选搜索</span>
        <el-button
          style="float:right"
          type="primary"
          @click="handleSearchList()"
          size="small">
          查询搜索
        </el-button>
        <el-button
          style="float:right;margin-right: 15px"
          @click="handleResetSearch()"
          size="small">
          重置
        </el-button>
      </div>
      <div style="margin-top: 15px">
        <el-form :inline="true" :model="listQuery" size="small" label-width="140px">
          <el-form-item label="输入搜索：">
            <el-input v-model="listQuery.keyword" class="input-width" placeholder="帐号/姓名" clearable></el-input>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
    <el-card class="operate-container" shadow="never">
      <i class="el-icon-tickets"></i>
      <span>数据列表</span>
      <el-button size="mini" class="btn-add" @click="handleAdd()" style="margin-left: 20px">添加</el-button>
    </el-card>
    <div class="table-container">
      <el-table ref="adminTable"
                :data="list"
                style="width: 100%;"
                v-loading="listLoading" border>
        <el-table-column label="编号" width="100" align="center">
          <template slot-scope="scope">{{scope.row.id}}</template>
        </el-table-column>
        <el-table-column label="帐号" align="center">
          <template slot-scope="scope">{{scope.row.username}}</template>
        </el-table-column>
        <el-table-column label="姓名" align="center">
          <template slot-scope="scope">{{scope.row.nickName}}</template>
        </el-table-column>
        <el-table-column label="邮箱" align="center">
          <template slot-scope="scope">{{scope.row.email}}</template>
        </el-table-column>
        <el-table-column label="添加时间" width="160" align="center">
          <template slot-scope="scope">{{scope.row.createTime}}</template>
        </el-table-column>
        <el-table-column label="最后登录" width="160" align="center">
          <template slot-scope="scope">{{scope.row.loginTime}}</template>
        </el-table-column>
        <el-table-column label="是否启用" width="140" align="center">
          <template slot-scope="scope">
            <el-switch
              @change="handleStatusChange(scope.$index, scope.row)"
              :active-value="1"
              :inactive-value="0"
              v-model="scope.row.status">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180" align="center">
          <template slot-scope="scope">
            <el-button size="mini"
                       type="text"
                       @click="handleSelectRole(scope.$index, scope.row)">分配角色
            </el-button>
            <el-button size="mini"
                       type="text"
                       @click="handleUpdate(scope.row.id,list)">
              编辑
            </el-button>
            <el-button size="mini"
                       type="text"
                       @click="handleDelete(scope.row.id,list)">删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="pagination-container">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="listQuery.current"
        :page-sizes="[5,10,15]"
        :page-size="listQuery.size"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </div>
    <el-dialog
      :title="isEdit?'编辑用户':'添加用户'"
      :visible.sync="dialogVisible"
      width="40%">
      <el-form :model="admin"
               ref="adminForm"
               label-width="150px" size="small">
        <el-form-item label="帐号：">
          <el-input v-model="admin.username" style="width: 250px"></el-input>
        </el-form-item>
        <el-form-item label="姓名：">
          <el-input v-model="admin.nickName" style="width: 250px"></el-input>
        </el-form-item>
        <el-form-item label="邮箱：">
          <el-input v-model="admin.email" style="width: 250px"></el-input>
        </el-form-item>
        <el-form-item label="密码：">
          <el-input v-model="admin.password"  type="password" style="width: 250px"></el-input>
        </el-form-item>
        <el-form-item label="备注：">
          <el-input v-model="admin.note"
                    type="textarea"
                    :rows="5"
                    style="width: 250px"></el-input>
        </el-form-item>
        <el-form-item label="是否启用：">
          <el-radio-group v-model="admin.status">
            <el-radio :label="1">是</el-radio>
            <el-radio :label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false" size="small">取 消</el-button>
        <el-button type="primary" @click="handleDialogConfirm()" size="small">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="分配角色"
      :visible.sync="allocDialogVisible"
      width="30%">
      <el-select v-model="allocRoleIds" multiple placeholder="请选择" size="small" style="width: 80%">
        <el-option
          v-for="item in allRoleList"
          :key="item.id"
          :label="item.name"
          :value="item.id">
        </el-option>
      </el-select>
      <span slot="footer" class="dialog-footer">
        <el-button @click="allocDialogVisible = false" size="small">取 消</el-button>
        <el-button type="primary" @click="handleAllocDialogConfirm()" size="small">确 定</el-button>
      </span>
    </el-dialog>
  </div>

</template>

<script>
  import {fetchAdminList, createAdmin, getAdminById, deleteAdmin, updateStatus} from "../../../api/ums/admin";

  export const defaultListQuery = {
    name: '',
    size: 5,
    current: 1
  }

  export const defaultAdmin ={
    id: null,
    username: '',
    password: '',
    nickName: '',
    email: '',
    note: '',
    status: 1
  }

  export default {
        name: "Admin",
        data() {
            return {
              listQuery: Object.assign({}, defaultListQuery),
              list: [],
              pageSizes: [5, 10, 15, 20],
              total: 0,
              pageSize:'',
              listLoading: false,
              dialogVisible: false,
              admin: Object.assign({}, defaultAdmin),
              isEdit: false,
              allocDialogVisible: false,
              allocRoleIds:[],
              allRoleList:[],
              allocAdminId:null
            }
        },
        methods: {
          //-------------查询数据
          getList(){
            fetchAdminList(this.listQuery).then(result => {
              //debugger
              this.list=result.data.records;
              this.total = result.data.total;
            })
          },
          //------------------上下页点击事件a+b
          handleSizeChange(val){
            this.listQuery.current = 1;
            this.listQuery.size = val;
            this.getList();
          },
          //---------------b
          handleCurrentChange(val){
            this.listQuery.current = val;
            this.getList();
          },
          //------------------新增用户弹框
          handleAdd(){
            this.admin = Object.assign({}, defaultAdmin);
            this.dialogVisible=true;
          },
          //------------------新增用户信息
          handleDialogConfirm(){
            createAdmin(this.admin).then(result => {
              if(result.code == 200){
                this.$message({
                  message: '数据编辑成功！',
                  type: 'success'
                });
              }
              this.dialogVisible = false;
              this.getList();
            })
          },
          //----------------------编辑
          handleUpdate(id){
            getAdminById(id).then(res =>{
              //debugger
              if(res.code == 200){
                this.admin = res.data;
                this.dialogVisible = true;
              }
            })
          },
          //删除
          handleDelete(id){
            deleteAdmin(id).then(res=>{
              if(res.code == 200){
                this.$message({
                  type: 'success',
                  message: '删除成功!'
                });
              }
              this.getList();
            })
          },
          //---------------分配角色
          handleSelectRole(){

          },
          //条件查询
          handleSearchList(){
            this.listQuery.current = 1;
            this.getList();
          },
          //重置方法
          handleResetSearch(){
            this.listQuery = Object.assign({}, defaultListQuery);
          },
          //是否启用
          handleStatusChange(index,row){
            this.$confirm('是否要修改该状态?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              updateStatus(row.id, row.status).then(response => {
                this.$message({
                  type: 'success',
                  message: '修改成功!'
                });
              });
            }).catch(() => {
              this.$message({
                type: 'info',
                message: '取消修改'
              });
              this.getList();
            });
          },

        },
        mounted() {
          this.getList();

        }
    }
</script>

<style scoped>

</style>
