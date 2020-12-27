// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import qs from 'qs'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import VueI18n from 'vue-i18n';
import { messages } from './components/common/i18n';
import md5 from 'js-md5';

import axios from 'axios'
Vue.prototype.$http = axios
import './assets/css/icon.css';
import './components/common/directives';
import 'babel-polyfill';

Vue.prototype.$md5 = md5;
Vue.use(ElementUI);
Vue.use(VueI18n);
Vue.prototype.$qs = qs;
Vue.config.productionTip = false
const i18n = new VueI18n({
  locale: 'zh',
  messages
});

//使用钩子函数对路由进行权限跳转
router.beforeEach((to, from, next) => {
  document.title = `${to.meta.title} | 狐狸`;
  const role = localStorage.getItem('token');
  if (!role && to.path !== '/Login') {
    next('/Login');
  } else if (to.meta.permission) {
    // 如果是管理员权限则可进入，这里只是简单的模拟管理员权限而已
    role === 'admin' ? next() : next('/403');
  } else {
    // 简单的判断IE10及以下不进入富文本编辑器，该组件不兼容
    if (navigator.userAgent.indexOf('MSIE') > -1 && to.path === '/editor') {
      Vue.prototype.$alert('vue-quill-editor组件不兼容IE10及以下浏览器，请使用更高版本的浏览器查看', '浏览器不兼容通知', {
        confirmButtonText: '确定'
      });
    } else {
      next();
    }
  }
});
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  i18n,
  components: { App },
  template: '<App/>'
})
