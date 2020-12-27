<template>
  <el-card class="form-container" shadow="never">
    <el-form :model="menu"
             :rules="rules"
             ref="menuFrom"
             label-width="150px">
      <el-form-item label="菜单名称：" prop="title">
        <el-input v-model="menu.title"></el-input>
      </el-form-item>
      <el-form-item label="上级菜单：">
        <el-select v-model="menu.parentId"
                   placeholder="请选择菜单">
          <el-option
            v-for="item in selectMenuList"
            :key="item.id"
            :label="item.title"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="前端名称：" prop="name">
        <el-input v-model="menu.name"></el-input>
      </el-form-item>
      <el-form-item label="前端图标：" prop="icon">
        <el-input v-model="menu.icon" style="width: 80%"></el-input>
        <svg-icon style="margin-left: 8px" :icon-class="menu.icon"></svg-icon>
      </el-form-item>
      <el-form-item label="是否显示：">
        <el-radio-group v-model="menu.hidden">
          <el-radio :label="0">是</el-radio>
          <el-radio :label="1">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="排序：">
        <el-input v-model="menu.sort"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit('menuFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('menuFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>

  import {createMenu, fetchMenuSelect, getMenuById} from "../../../../api/ums/menu";

  const defaultMenu = {
    title: '',
    parentId: 0,
    name: '',
    icon: '',
    hidden: 0,
    sort: 0
  };
    export default {
        name: "MenuDetail",
        props: {
          isEdit: {
            type: Boolean,
            default: false
          }
      },
        data() {
            return {
              menu: Object.assign({}, defaultMenu),
              selectMenuList: [],
              rules: {
                title: [
                  {required: true, message: '请输入菜单名称', trigger: 'blur'},
                  {min: 2, max: 140, message: '长度在 2 到 140 个字符', trigger: 'blur'}
                ],
                name: [
                  {required: true, message: '请输入前端名称', trigger: 'blur'},
                  {min: 2, max: 140, message: '长度在 2 到 140 个字符', trigger: 'blur'}
                ],
                icon: [
                  {required: true, message: '请输入前端图标', trigger: 'blur'},
                  {min: 2, max: 140, message: '长度在 2 到 140 个字符', trigger: 'blur'}
                ]
              }
            }
        },
      created() {
        if (this.isEdit) {
          let id = this.$route.query.id;
          getMenuById(id).then(response => {
            this.menu = response.data;
            debugger
          });
        } else {
          this.menu = Object.assign({}, defaultMenu);
        }
        this.getSelectMenuList();
      },
        methods: {
          //查询菜单
          getSelectMenuList() {
            fetchMenuSelect().then(response => {
              this.selectMenuList = response.data;
              this.selectMenuList.unshift({id: 0, title: '无上级菜单'});
            });
          },
          //新增菜单
          onSubmit(menuFrom){
            createMenu(this.menu).then(res=>{
              if(res.code == 200){
                this.$message({
                  message:"数据提交成功",
                  type:"success"
                })
              }
              this.$router.back();
            })
          },
          //重置
          resetForm(menuFrom){
            this.menu = Object.assign({}, defaultMenu);
            this.getSelectMenuList();
          }
        },
        mounted() {
          this.getSelectMenuList();
        }
    }
</script>

<style scoped>

</style>
