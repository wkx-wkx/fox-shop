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
            <el-input v-model="listQuery.keyword" class="input-width" placeholder="角色名称" clearable></el-input>
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
      <el-table ref="roleTable"
                :data="list"
                style="width: 100%;"
                v-loading="listLoading" border>
        <el-table-column label="编号" width="100" align="center">
          <template slot-scope="scope">{{scope.row.id}}</template>
        </el-table-column>
        <el-table-column label="角色名称" align="center">
          <template slot-scope="scope">{{scope.row.name}}</template>
        </el-table-column>
        <el-table-column label="描述" align="center">
          <template slot-scope="scope">{{scope.row.description}}</template>
        </el-table-column>
        <el-table-column label="用户数"  width="100" align="center">
          <template slot-scope="scope">{{scope.row.adminCount}}</template>
        </el-table-column>
        <el-table-column label="添加时间" width="160" align="center">
          <template slot-scope="scope">{{scope.row.createTime}}</template>
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
        <el-table-column label="操作" width="160" align="center">
          <template slot-scope="scope">
            <el-row>
              <el-button size="mini"
                         type="text"
                         @click="handleSelectMenu(scope.$index, scope.row)">分配菜单
              </el-button>
              <el-button size="mini"
                         type="text"
                         @click="handleSelectResource(scope.$index, scope.row)">分配资源
              </el-button>
            </el-row>
            <el-row>
              <el-button size="mini"
                         type="text"
                         @click="handleUpdate(scope.row.id,list)">
                编辑
              </el-button>
              <el-button size="mini"
                         type="text"
                         @click="handleDelete(scope.row.id,list)">删除
              </el-button>
            </el-row>
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
      :title="isEdit?'编辑角色':'添加角色'"
      :visible.sync="dialogVisible"
      width="40%">
      <el-form :model="role"
               ref="roleForm"
               label-width="150px" size="small">
        <el-form-item label="角色名称：">
          <el-input v-model="role.name" style="width: 250px"></el-input>
        </el-form-item>
        <el-form-item label="描述：">
          <el-input v-model="role.description"
                    type="textarea"
                    :rows="5"
                    style="width: 250px"></el-input>
        </el-form-item>
        <el-form-item label="是否启用：">
          <el-radio-group v-model="role.status">
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
  </div>
</template>

<script>

  import {createRole, deleteRole, fetchRoleList, getRoleById, updateStatus} from "../../../api/ums/role";

  const defaultListQuery = {
    current: 1,
    size: 5
  };
  const defaultRole = {
    id: null,
    name: null,
    description: null,
    adminCount: 0,
    status: 1
  };

    export default {
        name: "role",
        data() {
            return {
              listQuery: Object.assign({}, defaultListQuery),
              list: [],
              pageSizes:[5,10,15],
              total: 0,
              pageSize:'',
              listLoading: false,
              dialogVisible: false,
              role: Object.assign({}, defaultRole),
              isEdit: false
            }
        },
        methods: {
          getList(){
            fetchRoleList(this.listQuery).then(res=>{
              this.list=res.data.records;
              this.total=res.data.total;
            })

          },
          handleSizeChange(val){
            this.listQuery.current = 1;
            this.listQuery.size = val;
            this.getList();
          },
          handleCurrentChange(val){
            this.listQuery.current = val;
            this.getList();
          },
          //查询搜索
          handleSearchList(){

          },
          //重置
          handleResetSearch(){
            this.listQuery = Object.assign({}, defaultListQuery);
          },
          //添加的弹框
          handleAdd(){
            this.role = Object.assign({}, defaultRole);
            this.dialogVisible = true;
          },
          //确定
          handleDialogConfirm(){
            createRole(this.role).then(res=>{
              if(res.code == 200){
                this.$message({
                  message: '数据编辑成功！',
                  type: 'success'
                });
              }
                this.dialogVisible = false;
                this.getList();
            })
          },
          //编辑
          handleUpdate(id){
            getRoleById(id).then(res=>{
              if(res.code == 200){
                this.role = res.data;
                this.dialogVisible =true;
              }
            })
          },
          //删除
          handleDelete(id){
            deleteRole(id).then(res=>{
              if(res.code == 200){
                this.$message({
                  message:"删除成功",
                  type:"success"
                });
              }
              this.getList();
            })
          },
          //是否启用
          handleStatusChange(index,row){
            this.$confirm('是否要修改该状态?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              updateStatus(row.id,row.status).then(response => {
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
          //分配菜单
          handleSelectMenu(){

          },
          //分配资源
          handleSelectResource(){

          },

        },
        mounted() {
          this.getList();
        }
    }
</script>

<style scoped>

</style>
