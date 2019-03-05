import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'homepage',
      component: resolve => require(['@/components/public'],resolve),
      redirect:'/novel',
      children:[
        {
          path:'novel',
          name:'novel',
          component:resolve => require(['@/components/novel'],resolve)
        },
        {
          path:'complete',
          name:'complete',
          component:resolve => require(['@/components/complete'],resolve)
        },
        {
          path:'recommend',
          name:'recommend',
          component:resolve => require(['@/components/recommend'],resolve)
        },
        {
          path:'search',
          name:'search',
          component:resolve => require(['@/components/search'],resolve)
        },
        {
          path:'bookIndex',
          name:'bookIndex',
          component:resolve => require(['@/components/bookIndex'],resolve)
        },
        {
          path:'vBook',
          name:'vBook',
          component:resolve => require(['@/components/vBook'],resolve)
        },
        {
          path:'userInfo',
          name:'userInfo',
          component:resolve => require(['@/components/userInfo'],resolve)
        },
        {
          path:'alreadyRead',
          name:'alreadyRead',
          component:resolve => require(['@/components/alreadyRead'],resolve)
        }
      ]
    }
  ]
})
