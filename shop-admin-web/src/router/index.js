import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/view/Login'
import Home from '@/view/logout/Home'
import Brand from '@/view/pms/brand/BrandMain'
import Category from '@/view/pms/category/CategoryMain'
import ProductAttr from "../view/pms/attr/ProductAttr";
import AttrList from "../view/pms/attr/AttrList";
import ParamList from "../view/pms/attr/ParamList";
import Product from "../view/pms/product/Product";
import ProductAdd from "../view/pms/product/Add";
import ProductUpdate from "../view/pms/product/Update";
import HomePage from '@/view/HomePage';
import Admin from "../view/ums/admin/Admin";
import Role from "../view/ums/role/Role";
import Menu from "../view/ums/menu/Menu";
import Resource from "../view/ums/resource/Resource";
import Update from "../view/ums/menu/Update";
import Add from "../view/ums/menu/Add";
import CategoryList from "../view/ums/resource/CategoryList";

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/Login',
      name: 'Login',
      component: Login
    },
    {
      path: '/',
      component: Home,
      meta: { title: '系统首页' },
      children: [
        {
          path: '/dashboard',
          component: HomePage,
          name:"HomePage",
          meta: { title: '系统首页' }
        },
        {
          path: '/Product',
          component: Product,
          name: "Product",
          meta: { title: '商品列表' }
        },{
          path: '/ProductAdd',
          component: ProductAdd,
          name: "ProductAdd",
          meta: { title: '商品新增' }
        },
        {
          path: '/ProductUpdate',
          component: ProductUpdate,
          name: "ProductUpdate",
          meta: { title: '商品修改' }
        },
        {
          path: '/Brand',
          component: Brand,
          name: "Brand",
          meta: { title: '品牌管理' }
        },
        {
          path: '/Category',
          component: Category,
          name: "Category",
          meta: { title: '商品分类' }
        },
        {
          path: '/ProductAttr',
          component: ProductAttr,
          name: "ProductAttr",
          meta: { title: '商品类型' }
        },
        {
          path: '/AttrList',
          component: AttrList,
          name: "AttrList",
          meta: { title: '属性设置' }
        },
        {
          path: '/ParamList',
          component: ParamList,
          name: "ParamList",
          meta: { title: '参数设置' }
        },
        {
          path: '/admin',
          component: Admin,
          name: "Admin",
          meta: { title: '用户列表信息' }
        },
        {
          path: '/role',
          component: Role,
          name: "Role",
          meta: { title: '角色列表' }
        },
        {
          path: '/menu',
          component: Menu,
          name: "Menu",
          meta: { title: '菜单列表' }
        },
        {
          path: '/updateMenu',
          component: Update,
          name: "updateMenu",
          meta: { title: '修改菜单' }
        },
        {
          path: '/addMenu',
          component: Add,
          name: "addMenu",
          meta: { title: '新增菜单' }
        },
        {
          path: '/resource',
          component: Resource,
          name: "Resource",
          meta: { title: '资源列表' }
        },
        {
          path: '/resourceCategory',
          component: CategoryList,
          name: "resourceCategory",
          meta: { title: '资源分类' }
        }
      ]
    },
  ]
})
