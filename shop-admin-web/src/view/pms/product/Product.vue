<template>
 <!-- <div class="app-container">
    <el-card class="filter-container" shadow="never">
      <div>
        <i class="el-icon-search"></i>
        <span>筛选搜索</span>
        <el-button
          style="float: right"
          @click="handleSearchList()"
          type="primary"
          size="small">
          查询结果
        </el-button>
        <el-button
          style="float: right;margin-right: 15px"
          @click="handleResetSearch()"
          size="small">
          重置
        </el-button>
      </div>
      <div style="margin-top: 15px">
        <el-form :inline="true" :model="listQuery" size="small" label-width="140px">
          <el-form-item label="输入搜索：">
            <el-input style="width: 203px" v-model="listQuery.keyword" placeholder="商品名称"></el-input>
          </el-form-item>
          <el-form-item label="商品货号：">
            <el-input style="width: 203px" v-model="listQuery.productSn" placeholder="商品货号"></el-input>
          </el-form-item>
          <el-form-item label="商品分类：">
            <el-cascader
              clearable
              v-model="selectProductCateValue"
              :options="productCateOptions">
            </el-cascader>
          </el-form-item>
          <el-form-item label="商品品牌：">
            <el-select v-model="listQuery.brandId" placeholder="请选择品牌" clearable>
              <el-option
                v-for="item in brandOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="上架状态：">
            <el-select v-model="listQuery.publishStatus" placeholder="全部" clearable>
              <el-option
                v-for="item in publishStatusOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="审核状态：">
            <el-select v-model="listQuery.verifyStatus" placeholder="全部" clearable>
              <el-option
                v-for="item in verifyStatusOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
      </div>
    </el-card>-->
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-cascades"></i> 商品列表
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="handle-box">
        <el-button
          type="primary"
          icon="el-icon-circle-plus"
          class="handle-del mr10"
          @click="addProduct"
        >商品新增
        </el-button>
        <el-input v-model="query.name" placeholder="商品名称" class="handle-input mr10"></el-input>
        <el-button type="primary" icon="el-icon-search" @click="handleSearch">查询</el-button>
      </div>
      <el-table
        :data="tableData"
        border
        class="table"
        header-cell-class-name="table-header"
      >
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="id" label="编号" width="55" align="center"></el-table-column>
        <el-table-column label="商品图片" align="center">
          <template slot-scope="scope">
            <el-image
              class="table-td-thumb"
              :src="scope.row.pic"
              :preview-src-list="[scope.row.pic]"
            ></el-image>
          </template>
        </el-table-column>
        <el-table-column label="商品名称"  align="center">
          <template slot-scope="scope">
            <p>{{scope.row.name}}</p>
            <p>品牌:{{scope.row.brandName}}</p>
          </template>
        </el-table-column>
        <el-table-column label="价格/货号" width="120" align="center">
          <template slot-scope="scope">
            <p>价格:{{scope.row.price}}</p>
            <p>货号:{{scope.row.productSn}}</p>
          </template>
        </el-table-column>

        <el-table-column label="是否显示" width="120" align="center">
          <template slot-scope="scope">
            <p>上架:<el-switch
              @change="handlePublishStatusChange( scope.row)"
              :active-value="1"
              :inactive-value="0"
              v-model="scope.row.publishStatus">
            </el-switch></p>
            <p>新品:<el-switch
              @change="handleNewStatusChange( scope.row)"
              :active-value="1"
              :inactive-value="0"
              v-model="scope.row.newStatus">
            </el-switch></p>
            <p>推荐:<el-switch
              @change="handleRecommandStatusChange( scope.row)"
              :active-value="1"
              :inactive-value="0"
              v-model="scope.row.recommandStatus">
            </el-switch></p>
          </template>
        </el-table-column>
        <el-table-column prop="sort" label="排序"></el-table-column>
        <el-table-column prop="sale" label="销量"></el-table-column>
        <el-table-column label="审核状态" align="center">
          <template slot-scope="scope">
            <p v-if="scope.row.verifyStatus == 0">未审核</p>
            <p v-else>已审核</p>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180" align="center">
          <template slot-scope="scope">
            <el-button
              type="text"
              icon="el-icon-edit"
              @click="handleEdit(scope.row)"
            >编辑
            </el-button>
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
  </div>
</template>

<script>
  import {fetchProductData} from "../../../api/pms/product";
  export default {
        name: "Product",
        data() {
            return {
                query:{
                  name:"",
                  size:5,
                  current:1
                },
                tableData:[],
                pageSizes: [5, 10, 15, 20],
                editVisible: false,
                pageTotal: 0,
                idx: -1,
                id: -1
              }
        },
        methods: {
          // 获取 easy-mock 的模拟数据
          getData(){
            debugger
            fetchProductData(this.query).then(res => {
              this.tableData = res.data.records;
              this.pageTotal = res.data.total;
            });
          },
          // 触发搜索按钮
          handleSearch(){
            this.$set(this.query, 'current', 1);
            this.getData();
          },
          // 编辑操作
          handleEdit(row) {
            let id=row.id;
            this.$router.push({
              "path":"/ProductUpdate",
              "query":{
                productId:id
              }
            });
          },
          // 分页导航
          handlePageChange(val) {
            this.$set(this.query, 'current', val);
            this.getData();
          },
          handleSizeChange(val) {
            this.$set(this.query, 'current', 1);
            this.$set(this.query, 'size', val);
            this.getData();
          } ,
          handlePublishStatusChange(row) {
            let status = row.showStatus;
            let params = {
              id: row.id,
              showStatus:status
            }
            updateShowStatus(params).then(result=>{
              if(result.code === 200){
                this.$message.success("修改是否显示成功");
              }else{
                this.$message.error("修改是否显示失败");
              }
            })

          },
          handleNewStatusChange(row) {
            let status = row.factoryStatus;
            let params = {
              id: row.id,
              factoryStatus:status
            }
            updateFactoryStatus(params).then(result=>{
              if(result.code === 200){
                this.$message.success("修改是否品牌制造商成功");
              }else{
                this.$message.error("修改是否品牌制造商失败");
              }
            })
          },
          handleRecommandStatusChange(row) {
            let status = row.factoryStatus;
            let params = {
              id: row.id,
              factoryStatus:status
            }
            updateFactoryStatus(params).then(result=>{
              if(result.code === 200){
                this.$message.success("修改是否品牌制造商成功");
              }else{
                this.$message.error("修改是否品牌制造商失败");
              }
            })
          },
          /*点击事件，进行路由*/
          addProduct() {
            this.$router.push("/ProductAdd");
          }
        },
        mounted() {
          this.getData();
        }
    }
</script>

<style scoped>
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
