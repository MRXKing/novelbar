// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui';
import axios from 'axios'
import 'element-ui/lib/theme-chalk/index.css'
import VueLazyload from 'vue-lazyload'
import api from './api'
import store from './store'

Vue.use(VueLazyload)
Vue.use(ElementUI)
Vue.config.productionTip = false
Vue.prototype.$axios = axios
Vue.prototype.$api = api

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>',
  methods:{
    goBookIndex(book_name) {
      if (!sessionStorage.token) {
        this.$alert('请先登录', '提示', {
          confirmButtonText: '确定'
        })
      }else {
        this.$axios.get(this.$api.getUsername, {
          params: {
            token: sessionStorage.token
          }
        }).then(res => {
            localStorage.bookTitle = book_name
            let {href} = this.$router.resolve({name:'bookIndex'})
            window.open(href,'_brank')
        }).catch(err => {
          sessionStorage.removeItem('token')
          this.stat = false
          this.$notify({
           title: '请重新登录',
           message: 'token无效或过期'
         })
         setTimeout(()=>{
           location.reload()
         },500)
          console.log(err.response.data.msg.error)
        })
      }
    },
    goType(){
      this.$alert('暂未开发', '提示', {
        confirmButtonText: '确定'
      })
    }
  }
})
