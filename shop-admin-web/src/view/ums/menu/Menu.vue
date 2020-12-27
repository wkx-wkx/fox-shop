<template>
  <div class="app-container">
    <el-card class="operate-container" shadow="never">
      <i class="el-icon-tickets" style="margin-top: 5px"></i>
      <span style="margin-top: 5px">数据列表</span>
      <el-button
        class="btn-add"
        @click="handleAddMenu()"
        size="mini">
        添加
      </el-button>
    </el-card>
    <div class="table-container">
      <el-table ref="menuTable"
                style="width: 100%"
                :data="list"
                border>
        <el-table-column label="编号" width="100" align="center">
          <template slot-scope="scope">{{scope.row.id}}</template>
        </el-table-column>
        <el-table-column label="菜单名称" align="center">
          <template slot-scope="scope">{{scope.row.title}}</template>
        </el-table-column>
        <el-table-column label="菜单级数" width="100" align="center">
          <template slot-scope="scope">{{scope.row.level | levelFilter}}</template>
        </el-table-column>
        <el-table-column label="前端名称" align="center">
          <template slot-scope="scope">{{scope.row.name}}</template>
        </el-table-column>
        <el-table-column label="前端图标" width="100" align="center">
          <template slot-scope="scope"><svg-icon :icon-class="scope.row.icon"></svg-icon></template>
        </el-table-column>
        <el-table-column label="是否显示" width="100" align="center">
          <template slot-scope="scope">
            <el-switch
              @change="handleHiddenChange(scope.$index, scope.row)"
              :active-value="0"
              :inactive-value="1"
              v-model="scope.row.hidden">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column label="排序" width="100" align="center">
          <template slot-scope="scope">{{scope.row.sort }}</template>
        </el-table-column>
        <el-table-column label="设置" width="120" align="center">
          <template slot-scope="scope">
            <el-button
              v-if="scope.row.level == 0"
              size="mini"
              icon="el-icon-caret-bottom"
              @click="handleChildren(scope.row)"
            >查看下级</el-button>
            <el-button
              size="mini"
              v-else
              icon="el-icon-caret-top"
              @click="handleParent(scope.row)"
            >返回上级</el-button>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" align="center">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              @click="handleUpdate(scope.row)">编辑
            </el-button>
            <el-button
              size="mini"
              type="text"
              @click="handleDelete(scope.row)">删除
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
  </div>
</template>

<script>

  import {deleteMenu, fetchMenuList, updateHiddenById} from "../../../api/ums/menu";

    export default {
        name: "Menu",
        data() {
            return {
              list: [],
              total: 0,
              listLoading: true,
              listQuery: {
                  name:'',
                  current: 1,
                  size: 5,
                  pid:null,
                  level:0
              },
              parentId: 0
            }
        },
      methods: {
          getList(){
            fetchMenuList(this.listQuery).then(res =>{
              this.list = res.data.records;
              this.total = res.data.total;
            });
          },
          handleSizeChange(value){
            this.$set(this.listQuery, 'current', 1);
            this.$set(this.listQuery, 'size', value);
            this.getList();
          },
          handleCurrentChange(value){
            this.$set(this.listQuery, 'current', value);
            this.getList();
          },
          //新增
          handleAddMenu(row){
            this.$router.push("/addMenu");
          },
          //编辑
          handleUpdate(row){
            let id = row.id;
            //alert(id)
            this.$router.push({
              path:"/updateMenu",
              query:{id:id}
            });
          },
          //查看下级
          handleChildren(row){
            this.$set(this.listQuery,"pid",row.id);
            this.$set(this.listQuery,"level",null);
            this.$set(this.listQuery,'current',1);
            this.getList();
          },
          //查看上级
          handleParent(row){
            this.$set(this.listQuery,"pid",null);
            this.$set(this.listQuery,"level",0);
            this.$set(this.listQuery,'current',1);
            this.getList();
          },
          //是否显示
          handleHiddenChange(index,row){
            updateHiddenById(row.id,row.hidden).then(response=>{
              this.$message({
                message: '修改成功',
                type: 'success',
                duration: 1000
              });
            });
          },
        //删除,(最好判断拥有子集不能删除)
        handleDelete(row){
          deleteMenu(row.id).then(res =>{
            if(res.code == 200){
               this.$message({
                 message:"删除成功",
                 type:"success"
               });
            }
            this.getList();
          })
        },
        },
        mounted() {
          this.getList();
        },
      filters: {
        levelFilter(value) {
          if (value === 0) {
            return '一级';
          } else if (value === 1) {
            return '二级';
          }
        },
        disableNextLevel(value) {
          if (value === 0) {
            return false;
          } else {
            return true;
          }
        }
      }
    }
</script>

<style scoped>

</style>
