<template>
  <div>
    <div>
      <div class="crumbs">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item>
            <i class="el-icon-lx-cascades"></i> 品牌列表
          </el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <div class="container">
        <div class="handle-box">
          <el-button
            type="primary"
            icon="el-icon-delete"
            class="handle-del mr10"
            @click="delAllSelection">
            批量删除</el-button>
          <el-button
            type="primary"
            icon="el-icon-plus"
            class="handle-del mr10"
            @click="toAddBrand">
            新增品牌</el-button>
          <el-input v-model="query.name" placeholder="品牌名称" class="handle-input mr10"></el-input>
          <el-button type="primary" icon="el-icon-search" @click="handleSearch">查询</el-button>
        </div>
        <el-table
          :data="tableData"
          border
          class="table"
          ref="multipleTable"
          :row-class-name="table-header"
          @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center"></el-table-column>
          <el-table-column prop="id" label="编号" width="55" align="center"></el-table-column>
          <el-table-column prop="name" label="品牌名称"></el-table-column>
          <el-table-column prop="firstLetter" label="首字母"></el-table-column>
          <el-table-column prop="sort" label="排序"></el-table-column>
          <el-table-column label="头像(查看大图)" align="center">
            <template slot-scope="scope">
              <el-image
                class="table-td-thumb"
                :src="scope.row.logo"
                :preview-src-list="[scope.row.logo]"
              ></el-image>
            </template>
          </el-table-column>
          <el-table-column label="品牌制造商" width="100" align="center">
            <template slot-scope="scope">
              <el-switch
                @change="handleFactoryStatusChange(scope.$index, scope.row)"
                :active-value="1"
                :inactive-value="0"
                v-model="scope.row.factoryStatus">
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
          <el-table-column label="操作" width="180" align="center">
            <template slot-scope="scope">
              <el-button
                type="text"
                icon="el-icon-edit"
                @click="handleEdit(scope.row.id,tableData)"
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
        <!--新增-->
        <el-dialog :title="brandTitle" :visible.sync="editVisible">
          <el-form :model="brandForm" ref="brandForm">
            <el-form-item label="品牌名称" prop="name">
              <el-input v-model="brandForm.name" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="首字母" prop="firstLetter">
              <el-input v-model="brandForm.firstLetter" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="排序" prop="sort">
              <el-input v-model="brandForm.sort" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="品牌logo"
                          prop="logo">
              <single-upload v-model="brandForm.logo"></single-upload>
            </el-form-item>
            <el-form-item label="品牌赞助商" prop="factoryStatus">
              <el-radio-group v-model="brandForm.factoryStatus">
                <el-radio :label="0">否</el-radio>
                <el-radio :label="1">是</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="是否显示" prop="showStatus">
              <el-radio-group v-model="brandForm.showStatus">
                <el-radio :label="1">是</el-radio>
                <el-radio :label="2">否</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="editVisible = false">取 消</el-button>
            <el-button type="primary" @click="addBrand">确 定</el-button>
          </div>
        </el-dialog>
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

    </div>
  </div>
</template>

<script>
  import { fetchBrandData } from '../../../api/pms/brand'
  import SingleUpload from "../../../components/upload/SingleUpload";
  export default {
    name: "BrandMain",
    components: {SingleUpload},
    data() {
      return {
        query: {
          size: 5,
          current: 1,
          brandTitle:""
        },
        brandForm:{
           name: '',
           firstLetter:'',
           sort:'',
           factoryStatus:'0',
           showStatus:'1',
           productCount:'',
           productCommentCount:'',
           logo:'',
           bigPic:'',
           brandStory:''
        },
        tableData: [],
        brandTitle:'',
        logo:'',
        multipleSelection: [],
        delList: [],
        pageSizes:[5,10,15,20],
        editVisible: false, /*弹框*/
        pageTotal: 0,
        form: {},
        idx: -1,
        id: -1
      }
    },
    methods: {
      // 获取 easy-mock 的模拟数据
      getData() {
        fetchBrandData(this.query).then(res => {
          this.tableData = res.data.records;
          this.pageTotal =  res.data.total ;
        });
      },
      /*点击新增*/
      toAddBrand(){
        /*获取数据弹框*/
        this.editVisible = true;
    },
      /*新增*/
      addBrand(){
        alert(this.brandForm.name)
        debugger
        this.$http({
          url:'/fox/pms/pmsBrand',
          method:'post',
          params:this.brandForm
        }).then(res=>{
          if(res.data.code == 200){
            this.$message({
                message:'数据保存成功',
                type:'successs'
            });
            this.getData();
            this.editVisible = false;
          }else{

        }
        })
      },
      // 触发搜索按钮
      handleSearch() {
        this.$set(this.query, 'pageIndex', 1);
        this.getData();
      },
      // 删除操作
      handleDelete(index, row) {
        // 二次确认删除
        this.$confirm('确定要删除吗？', '提示', {
          type: 'warning'
        }).then(() => {
            this.$message.success('删除成功');
            this.tableData.splice(index, 1);
          }).catch(() => {});
      },
      // 多选操作
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      delAllSelection() {
        const length = this.multipleSelection.length;
        let str = '';
        this.delList = this.delList.concat(this.multipleSelection);
        for (let i = 0; i < length; i++) {
          str += this.multipleSelection[i].name + ' ';
        }
        this.$message.error(`删除了${str}`);
        this.multipleSelection = [];
      },
      // 编辑操作
      handleEdit(id) {
        alert(id)
        this.editVisible=true;
        this.$http({
          url:"/fox/pms/pmsBrand/a/"+id,
          method:"get"
        }).then(res=>{
          if(res.data.code == 200){
            this.brandForm = res.data.data;
          }else{
            this.editVisible = false;
          }
        })
      },
      // 分页导航
      handlePageChange(val) {
        this.$set(this.query, 'current', val);
        this.getData();
      },
      handleSizeChange(val){
        this.$set(this.query, 'current', 1);
        this.$set(this.query, 'size', val);
        this.getData();
      }
      ,handleFactoryStatusChange(val){

      },
      handleShowStatusChange(val){

      },
    },
    mounted() {
      this.getData();
    }
  }
</script>

<style >
  .handle-box {
    margin-bottom: 20px;
  }

  .handle-select {
    width: 120px;
  }

  .handle-input {
    width: 300px;
    display: inline-block;
  }
  .table {
    width: 100%;
    font-size: 14px;
  }
  .red {
    color: #ff0000;
  }
  .mr10 {
    margin-right: 10px;
  }
  .table-td-thumb {
    display: block;
    margin: auto;
    width: 40px;
    height: 40px;
  }

</style>
