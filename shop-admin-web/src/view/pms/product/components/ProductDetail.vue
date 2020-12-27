<template>
  <el-card class="form-container" shadow="never">
    <el-steps :active="active" finish-status="success" align-center>
      <el-step title="填写商品信息"></el-step>
      <el-step title="填写商品促销"></el-step>
      <el-step title="填写商品属性"></el-step>
      <el-step title="选择商品关联"></el-step>
    </el-steps>
    <!--基本信息-->
    <product-info-detail
      v-show="showStatus[0]"
      :is-edit="isEdit"
      v-model="productForm"
      @nextStep="nextStep"></product-info-detail>
    <!--促销信息-->
    <product-sale-detail
      v-show="showStatus[1]"
      :is-edit="isEdit"
      v-model="productForm"
      @prevStep="prevStep"
      @nextStep="nextStep"><!--绑定下一步的事件-->
    </product-sale-detail>
    属性信息
    <product-attr-detail
      v-show="showStatus[2]"
      :is-edit="isEdit"
      v-model="productForm"
      @prevStep="prevStep"
      @nextStep="nextStep">
    </product-attr-detail>
    <!--关联信息-->
    <product-relation-detail
      v-show="showStatus[3]"
      :is-edit="isEdit"
      @prevStep="prevStep"
      @finishCommit="finishCommit">
    </product-relation-detail>
  </el-card>
</template>

<script>
  /*导入页面，在下面使用1*/
  import ProductInfoDetail from './ProductInfoDetail';
  import ProductAttrDetail from './ProductAttrDetail';
  import ProductRelationDetail from './ProductRelationDetail';
  import ProductSaleDetail from './ProductSaleDetail';
  import {saveOrUpdateProductData,getProductById} from "../../../../api/pms/product";

  /*绑定数据*/
  const defaultProductParam={
    /*查询表单全部数据*/
    albumPics: '',
    brandId: null,
    brandName: '',
    deleteStatus: 0,
    description: '',
    detailDesc: '',
    detailHtml: '',
    detailMobileHtml: '',
    detailTitle: '',
    feightTemplateId: 0,
    flashPromotionCount: 0,
    flashPromotionId: 0,
    flashPromotionPrice: 0,
    flashPromotionSort: 0,
    giftPoint: 0,
    giftGrowth: 0,
    keywords: '',
    lowStock: 0,
    name: '',
    newStatus: 0,
    note: '',
    originalPrice: 0,
    pic: '',
    previewStatus: 0,
    price: 0,
    productAttributeCategoryId: null,
    //一级id
    cateParentId:null,
    //二级id
    productCategoryId: null,
    productCategoryName: '',
    productSn: '',
    promotionEndTime: '',
    promotionPerLimit: 0,
    promotionPrice: null,
    promotionStartTime: '',
    promotionType: 0,
    publishStatus: 0,
    recommandStatus: 0,
    sale: 0,
    serviceIds: '',
    sort: 0,
    stock: 0,
    subTitle: '',
    unit: '',
    usePointLimit: 0,
    verifyStatus: 0,
    weight: 0,
    //会员价格集合
    memberPriceList:[],
    //阶梯价格维护
    productLadderList:[{count: 0,discount: 0,price: 0}],
    //满减价格
    productFullReductionList:[{fullPrice: 0, reducePrice: 0}],
    //SKU的维护
    skuStockList: [],
    //商品的属性和参数列表
    productAttributeValueList:[]
  }
  export default {
    name: "ProductDetail",
    /*在这儿使用1*/
    components:{ProductInfoDetail,ProductAttrDetail,ProductRelationDetail,ProductSaleDetail},
    props: {
      /*:is-edit="isEdit"与上面绑定*/
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        /*展示的状态*/
        showStatus: [true, false, false, false],
        /*结果绑定，默认第一个被选中为0*/
        active:0,
        /*表单初始化*/
        productForm:Object.assign({},defaultProductParam)
      }
    },
    methods: {
      /*4个表单隐藏，需要展示的则进行展示,如何隐藏全部是通过展示状态进行控制的*/
      hideAll(){
        for(let i=0;i<this.showStatus.length;i++){
          this.showStatus[i]=false;
        }
      },
      /*绑定下一步事件*/
      nextStep(){
        /*判断第一次的状态,前三个表单触发此方法*/
        if(this.active<this.showStatus.length-1){
          this.hideAll();
          /*这个是当前*/
          this.active++;
          //根据showStatus来判断true或者false
          this.showStatus[this.active]=true;
        }
      },
      /*绑定上一步事件*/
      prevStep(){
        this.hideAll();
        this.active--;
        this.showStatus[this.active]=true;
      },
      /*最后的提交*/
      finishCommit(){
        saveOrUpdateProductData(this.productForm).then(result=>{
          debugger
          if(result.code === 200){
            this.$message.success("保存商品的数据成功");
            this.$router.push("/Product");
          }else{
            this.$message.success("保存商品的数据失败");
          }
        })
      }

    },
    mounted() {
      if(this.isEdit){
        getProductById(this.$route.query.productId).then(result=>{
          this.productForm=result.data;
        })
      }
    }
  }
</script>

<style scoped>

</style>
