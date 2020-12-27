<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-cascades"></i> 商品分类
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="handle-box">
        <el-button
          type="primary"
          icon="el-icon-delete"
          class="handle-del mr10"
          @click="delAllSelection"
        >批量删除</el-button>
        <el-input v-model="query.name" placeholder="品牌名称" class="handle-input mr10"></el-input>
        <el-button type="primary" icon="el-icon-search" @click="handleSearch">查询</el-button>
      </div>
      <el-table
        :data="tableData"
        border
        class="table"
        ref="multipleTable"
        header-cell-class-name="table-header"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="id" label="编号" width="55" align="center"></el-table-column>
        <el-table-column prop="name" label="分类名称"></el-table-column>
        <el-table-column prop="level" label="级别">
          <template slot-scope="scope">
            <span v-if="scope.row.level == 0">一级</span>
            <span v-else>二级</span>
          </template>
        </el-table-column>

        <el-table-column label="分类图标" align="center">
          <template slot-scope="scope">
            <el-image v-if="scope.row.icon != null"
                      class="table-td-thumb"
                      :src="scope.row.icon"
                      :preview-src-list="[scope.row.icon]"
            ></el-image>
          </template>
        </el-table-column>
        <el-table-column prop="productCount" label="商品数量"></el-table-column>
        <el-table-column prop="productUnit" label="数量单位"></el-table-column>
        <el-table-column label="导航栏" width="100" align="center">
          <template slot-scope="scope">
            <el-switch
              @change="handleShowStatusChange(scope.$index, scope.row)"
              :active-value="1"
              :inactive-value="0"
              v-model="scope.row.navStatus">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column label="是否显示" width="100" align="center">
          <template slot-scope="scope">
            <el-switch
              @change="handleShowStatusChange(scope.$index, scope.row)"
              :active-value="1"
              :inactive-value="0"
              v-model="scope.row.showStatus">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column prop="sort" label="排序"></el-table-column>

        <el-table-column label="设置" width="180" align="center">
          <template slot-scope="scope">
            <el-button
              v-if="scope.row.level == 0"
              size="mini"
              icon="el-icon-caret-bottom"
              @click="handleChildren( scope.row)"
            >查看下级</el-button>

            <el-button
              size="mini"
              v-else
              icon="el-icon-caret-top"
              @click="handleParent( scope.row)"
            >返回上级</el-button>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180" align="center">
          <template slot-scope="scope">
            <el-button
              type="text"
              icon="el-icon-edit"
              @click="handleEdit(scope.$index, scope.row)"
            >编辑</el-button>
            <el-button
              type="text"
              icon="el-icon-delete"
              class="red"
              @click="handleDelete(scope.$index, scope.row)"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination
          background
          layout="total, sizes, prev, pager, next, jumper"
          :current-page="query.current"
          :page-size="query.size"
          :page-sizes="pageSizes"
          :total="pageTotal"
          @current-change="handlePageChange"
          @size-change="handleSizeChange"
        ></el-pagination>
      </div>
    </div>
    <!-- 编辑弹出框 -->
    <el-dialog title="编辑" :visible.sync="editVisible" width="30%">
      <el-form ref="form" :model="form" label-width="70px">
        <el-form-item label="用户名">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="form.address"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
    </el-dialog>
  </div>
</template>

<script>
  import {fetchCategoryData, getCategoryById} from "../../../api/pms/category";

  export default {
    name: "CategoryMain",
    data() {
      return {
        query: {
          name: '',
          size: 5,
          level:0,
          pid:null,
          current: 1
        },
        tableData: [],
        multipleSelection: [],
        delList: [],
        pageSizes:[5,10,15,20],
        editVisible: false,
        pageTotal: 0,
        form: {},
        idx: -1,
        id: -1
      }
    },
    methods: {
      // 获取 easy-mock 的模拟数据
      getData() {
        fetchCategoryData(this.query).then(res => {
          this.tableData = res.data.records;
          this.pageTotal = res.data.total;
        });
      },
      handleChildren(row){
        this.$set(this.query, 'pid', row.id);
        this.$set(this.query, 'level', null);
        this.$set(this.query, 'current', 1);
        this.getData();
      }
      ,handleParent(row){
        this.$set(this.query, 'pid', null);
        this.$set(this.query, 'level', 0);
        this.$set(this.query, 'current', 1);
        this.getData();
      }
      ,handlePageChange(val){
        this.$set(this.query, 'current', val);
        this.getData();
      },
      handleSizeChange(val){
        this.$set(this.query, 'current', 1);
        this.$set(this.query, 'size', val);
        this.getData();
      },
      handleEdit(row){
      /*  let id=rwo.id;
        getCategoryById(id).then(result=>{
          if(result.code === 200){
            this.categoryForm =result.data;
            this.titleName="分类修改";
            this.queryParentCategoryList();
            this.editVisible = true;
          }else{
            this.$message.error("数据查询失败");
          }
        })*/

      },
    },
    mounted() {
      this.getData();
    }
  }
</script>

<style>

</style>
