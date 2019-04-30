<template>
<div id="wrapper" v-loading="loading">
  <div class="navbar">
    <div class="">
      <img v-lazy='logo' alt="" @click="goHome">
    </div>
    <div>
      <el-input placeholder="请输入内容" v-model="state.search" clearable>
        <el-button slot="append" icon="el-icon-search" @click="goSearch"></el-button>
      </el-input>
    </div>
  </div>
  <el-container>
    <el-header>
      <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect" background-color="#545c64" text-color="#fff" active-text-color="#ffd04b">
        <el-submenu index="0" :disabled="this.$store.state.disabled">
          <template slot="title" >全部分类</template>
          <el-menu-item v-for="(value,index) in type" :index="0+'-'+(index+1)" :key="index" @click="$root.goType">{{value}}</el-menu-item>
        </el-submenu>
        <el-menu-item v-for="(value,index) in navbar" :index="(index+1).toString()" :key="index" @click.native="jump(value)">{{value}}</el-menu-item>
        <el-menu-item index="3" style="float:right">
          <div class="login">
            <span @click="dialogFormVisible = true" v-show="!stat">登录</span>
            <span @click="dialogFormVisible2 = true" v-show="!stat">注册</span>
            <el-dropdown v-show="stat">
              <span class="el-dropdown-link">
                <img v-lazy="head" alt=""><span>{{username}}</span>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item @click.native="goUserInfo">个人信息</el-dropdown-item>
                <el-dropdown-item @click.native="alreadyRead">追书情况</el-dropdown-item>
                <el-dropdown-item @click.native="logout">退出</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </el-menu-item>
      </el-menu>
    </el-header>

  </el-container>
  <router-view />
  <el-footer>
    <div class="footer">
      <div class="footer_top">
        <div class="footer_top_left">
          <ul>
            <li v-for="(value,index) in footer_left">{{value}}</li>
          </ul>
        </div>
        <div class="footer_top_right">
          <ul>
            <li>联系我们</li>
            <li> <img v-lazy="mail" alt=""> 110-10086</li>
            <li> <img v-lazy="phone" alt=""> 2388282560@qq.com</li>
          </ul>
        </div>
      </div>
      <div class="line"></div>
      <div class="footer_bottom">
        <div class="footer_bottom_left">
          <img v-lazy="wechat" alt="">
          <img v-lazy="xinlang" alt="">
        </div>
        <div class="footer_bottom_right" style="text-align:right;line-height:48px;color:rgb(162, 162, 162)">
          Copyright (c) 2018-2019 Copyright Holder All Rights Reserved.
        </div>
      </div>
    </div>
  </el-footer>
  <el-dialog title="login" :visible.sync="dialogFormVisible" v-loading="loading3">
    <el-form :model="form">
      <el-form-item label="用户名" :label-width="formLabelWidth">
        <el-input v-model.trim="form.username" autocomplete="off" clearable @keyup.enter.native="login"></el-input>
      </el-form-item>
      <el-form-item label="密码" :label-width="formLabelWidth">
        <el-input v-model.trim="form.pwd" autocomplete="off" type="password" clearable @keyup.enter.native="login"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <span style="float:left;margin-left:80px;">{{result1}}</span>
      <el-button @click="dialogFormVisible = false">取 消</el-button>
      <el-button type="primary" @click="login">确 定</el-button>
    </div>
  </el-dialog>
  <el-dialog title="register" :visible.sync="dialogFormVisible2" v-loading="loading2">
    <el-form :model="form2">
      <el-form-item label="用户名" :label-width="formLabelWidth">
        <el-input v-model.trim="form2.username" autocomplete="off" clearable @keyup.enter.native="register"></el-input>
      </el-form-item>
      <el-form-item label="密码" :label-width="formLabelWidth">
        <el-input v-model.trim="form2.pwd" autocomplete="off" type="password" clearable @keyup.enter.native="register"></el-input>
      </el-form-item>
      <el-form-item label="再输入一次密码" :label-width="formLabelWidth">
        <el-input v-model.trim="form2.againpwd" autocomplete="off" type="password" clearable @keyup.enter.native="register"></el-input>
      </el-form-item>
      <el-form-item label="年龄" :label-width="formLabelWidth">
        <el-input v-model.trim="form2.age" autocomplete="off" clearable @keyup.enter.native="register"></el-input>
      </el-form-item>
      <el-form-item label="生日" :label-width="formLabelWidth">
        <el-date-picker v-model="form2.birth" type="date" placeholder="选择日期" format="yyyy 年 MM 月 dd 日" value-format="yyyy-MM-dd">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="性别" :label-width="formLabelWidth">
        <el-radio v-model="form2.sex" label="男">男</el-radio>
        <el-radio v-model="form2.sex" label="女">女</el-radio>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <span style="float:left;margin-left:80px;">{{result2}}</span>
      <el-button @click="dialogFormVisible2 = false">取 消</el-button>
      <el-button type="primary" @click="register">确 定</el-button>
    </div>
  </el-dialog>
</div>
</template>

<script>
import {mapState} from 'vuex'
export default {
  data() {
    return {
      navbar: ['完本', '推荐'],
      logo: require('@/assets/logo.png'),
      wechat: require('@/assets/wechat.png'),
      xinlang: require('@/assets/xinlang.png'),
      head: require('@/assets/head.jpg'),
      footer_left: [
        '关于我们',
        '加入我们',
        '参与开发',
        '帮助'
      ],
      mail: require('@/assets/mail.png'),
      phone: require('@/assets/phone.png'),
      dialogFormVisible: false,
      form: {
        username: '',
        pwd: ''
      },
      stat: false,
      dialogFormVisible2: false,
      form2: {
        username: '',
        pwd: '',
        againpwd: '',
        sex: '',
        age: '',
        birth: ''
      },
      formLabelWidth: '120px',
      loading: true,
      loading2: false,
      loading3: false,
      result1: '',
      result2: '',
      username: '',
      activeIndex: '0',
      type:[
        '玄幻',
        '奇幻',
        '武侠',
        '仙侠',
        '都市',
        '现实',
        '军事',
        '历史',
        '游戏',
        '体育',
        '科幻',
        '悬疑灵异',
        '女生网',
        '二次元'
      ]
    }
  },
  computed:{
    ...mapState({state : state => state })
  },
  watch:
  {
    state:{
      deep: true,
        handler(state) {
            this.$store.commit('setSearch', state.search)
        }
    }
  },
  created() {
    if (sessionStorage.token && sessionStorage.token != null) {
      this.$axios.get(this.$api.getUsername, {
        params: {
          token: sessionStorage.token
        }
      }).then(res => {
        this.username = res.data.msg.username
        this.stat = true
      }).catch(err => {
        sessionStorage.removeItem('token')
        this.stat = false
        this.$notify({
         title: '请重新登录',
         message: 'token无效或过期'
       })
       if (this.$route.name == "bookIndex" || this.$route.name == "vBook") {
         this.$alert('请先登录', '提示', {
           confirmButtonText: '确定',
           callback:()=>{
              this.$router.push({name:'novel'})
           }
         })
       }
      })
    }
  },
  mounted() {
    Notification.requestPermission().then(function(permission) {
        if(permission === 'granted'){
          let notice = new Notification('novelbar',{
              body: '欢迎来到小说吧!',
              tag: 'linxin',
              icon: require('@/assets/logo.png')
          })
          setTimeout(()=>{
            notice.close()
          },2000)
        }
    })
    if (!sessionStorage.token && this.$route.name =="bookIndex") {
      this.$alert('请先登录', '提示', {
        confirmButtonText: '确定',
        callback:()=>{
          this.$router.push({name:'novel'})
        }
      })
    }
    if (!sessionStorage.token && this.$route.name =="vBook") {
      this.$alert('请先登录', '提示', {
        confirmButtonText: '确定',
        callback:()=>{
          history.back()
        }
      })
    }
    setTimeout(() => {
      this.loading = false;
    }, 500)
    if (this.$route.path == "/novel" || this.$route.path == "/") {
       this.$store.state.disabled = true
    }else {
      this.$store.state.disabled = false
    }
    if (this.$route.path == "/complete") {
      this.activeIndex = '1'
    }else if (this.$route.path == "/recommend") {
      this.activeIndex = '2'
    }
    if (this.$route.name == "search" || this.$route.name == "bookIndex" || this.$route.name == "vBook" ) {
      $('.footer').css('display','none')
    }else {
      $('.footer').css('display','block')
    }
  },
  methods: {
    goHome(){
      this.$router.push({path:'/novel'})
    },
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
    goUserInfo(){
      // let {href} = this.$router.resolve({name:'userInfo'})
      // window.open(href,'_blank')
      this.$router.push({name:'userInfo'})
    },
    alreadyRead(){
      // let {href} = this.$router.resolve({name:'alreadyRead'})
      // window.open(href,'_blank')
      this.$router.push({name:'alreadyRead'})
    },
    goSearch(){
       if (this.$route.name != 'search') {
         this.$router.push({name:'search'})
       }
       this.$root.$emit('goSearch')
    },
    jump(value){
      console.log(value);
      switch (value) {
        case "完本":
          this.$router.push({path:'/complete'})
          break;
        case "推荐":
          this.$router.push({path:'/recommend'})
      }
    },
    register() {
      this.loading2 = true
      this.$axios.post(this.$api.register, {
        username: this.form2.username,
        pwd: this.form2.pwd,
        againpwd: this.form2.againpwd,
        sex: this.form2.sex,
        birth: this.form2.birth,
        age: this.form2.age
      }).then(res => {
        this.loading2 = false
        this.result2 = res.data.success
        setTimeout(() => {
          window.location.reload()
        }, 500)
      }).catch(err => {
        this.loading2 = false
        this.result2 = err.response.data.msg.detail
      })
    },
    login() {
      this.loading3 = true
      this.$axios.post(this.$api.login, {
        username: this.form.username,
        pwd: this.form.pwd
      }).then(res => {
        this.loading3 = false
        this.result1 = res.data.success
        sessionStorage.token = res.data.msg.token
        setTimeout(() => {
          window.location.reload()
        }, 500)
      }).catch(err => {
        this.loading3 = false
        this.result1 = err.response.data.msg.detail
      })
    },
    logout() {
      this.$axios.get(this.$api.logout, {
        params: {
          token: sessionStorage.token
        }
      }).then(res => {
        sessionStorage.removeItem('token')
        setTimeout(() => {
          window.location.reload()
        }, 500)
      }).catch(err => {
        console.log(err);
      })

    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
#wrapper {
  width: 100%;
  min-height: 100%;
  height: 100%;
}

.navbar {
  width: 1200px;
  height: 150px;
  margin: 0 auto;
  display: flex;
  justify-content: center;
}

.el-header {
  background: rgb(84, 92, 100);
  padding: 0;
}

.navbar .el-input {
  width: 100%;
  margin-left: 10px;
  height: 40px;
  margin-top: calc(50% - 140px);
}

.navbar .el-input .el-button {
  background: rgb(84, 92, 100);
  color: #fff;
  border-radius: 0;
}

.navbar div {
  width: 33%;
  float: left;
  height: 100%;
}

.navbar div img {
  height: 100%;
  cursor: pointer;
}

.el-menu {
  width: 1200px;
  margin: 0 auto;
}
.el-menu * {
  border-bottom: none !important;
  height: 60px;
}
.el-menu .login>span {
  cursor: pointer;
}

.el-menu .login>span:hover {
  color: #ccc;
}

.el-menu .login .el-dropdown span {
  display: flex;
  cursor: pointer;
  color: #fff;
  justify-content: center;
  align-items: center;
}
.el-menu .login .el-dropdown span span{
  max-width: 50px;
  display: block;
  white-space:nowrap;
  overflow:hidden;
  word-break:keep-all;
  text-overflow:ellipsis;
}
.el-menu .login img {
  width: 30px;
  height: 30px;
  border-radius: 50%;
}

.el-menu .head li {
  width: 100px;
  color: #fff;
  justify-content: center;
  cursor: pointer;
  list-style: none;
  display: flex;
  align-items: center;
  height: 100%;
  transition: all 0.2s linear;
}

.el-menu .head li:hover {
  background: #e02c2c;
  border-top: 4px solid #000;
}

.actived {
  background: #e02c2c;
  margin-right: 40px;
}

.el-footer {
  width: 100%;
  height: auto !important;
  background-color: #292d2f;
  padding: 0;
}

.footer {
  width: 1200px;
  margin: 0 auto;
  height: 250px;
  background-color: #292d2f;
}

.footer .footer_top {
  width: 100%;
  height: 200px;
  display: flex;
  justify-content: space-between;
}

.footer .footer_top li {
  list-style: none;
  color: rgb(162, 162, 162);
}

.footer .footer_top ul li:first-child {
  font-weight: bold;
  font-size: 20px;
}

.footer .footer_top ul {
  display: flex;
  width: 100%;
  height: 100%;
  flex-direction: column;
  justify-content: space-around;
}

.footer .footer_top .footer_top_right ul li {
  display: flex;
  align-items: center;
  justify-content: flex-end;
}

.footer .footer_top .footer_top_left,
.footer .footer_top .footer_top_right {
  flex: 1;
}

.footer .line {
  width: 100%;
  height: 2px;
  background-color: #fff;
}

.footer .footer_bottom {
  width: 100%;
  height: 48px;
  display: flex;
  justify-content: space-between;
}

.footer .footer_bottom .footer_bottom_left,
.footer .footer_bottom .footer_bottom_right {
  flex: 1;
}
</style>
