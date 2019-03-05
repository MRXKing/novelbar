<template>
  <div class="box">
    <div class="boxinfo">
        <h3>个人信息</h3>
        <div class="userinfo">
          <div class="info_left">
              <img src="~@/assets/head.jpg" alt="">
              <el-button type="text" @click="changHead">修改</el-button>
          </div>
          <div class="info_right">
              <div class="first mr-20">
                ID:{{userId || '暂无'}}
              </div>
              <div class="second pb-30 mr-20">
                <i class="iconfont vuejs-shu"></i> : {{userReadBook || '0'}}本
              </div>
              <div class="third">
                <ul>
                  <li>昵称:{{userInfo.username || '暂无'}}<el-button type="text" @click="changHead">修改资料</el-button></li>
                  <li>生日:{{userInfo.birth || '暂无'}}</li>
                  <li>性别:{{userInfo.sex || '暂无'}}</li>
                  <li>年龄:{{userInfo.age || '暂无'}}</li>
                  <li>行业:</li>
                  <li>地区:</li>
                  <li>职位:</li>
                  <li>实名:</li>
                </ul>
              </div>
          </div>
        </div>
    </div>
  </div>
</template>

<script>
export default {
  data(){
    return {
      userId:'',
      userInfo:'',
      userReadBook:''
    }
  },
  methods:{
    changHead(){
      this.$alert('暂未开发', '提示', {
        confirmButtonText: '确定'
      })
    }
  },
  mounted(){
    this.$axios.get(this.$api.getUsername, {
      params: {
        token: sessionStorage.token
      }
    }).then(res => {
      this.$axios.get(this.$api.getUserInfo,{
        params:{token:sessionStorage.token}
      }).then(res => {
        this.userId = res.data.userId
        this.userReadBook = res.data.userReadBook
        this.userInfo = res.data.userInfo
      }).catch(err => {
        console.log(err);
      })
    }).catch(err => {
      this.$router.push({name:'novel'})
   })
  }
}
</script>

<style scoped>
.box
{
  width: 100%;
  background: #f5f6f7;
  padding: 20px 0;
}
.box .boxinfo
{
  width:1000px;
  margin:0 auto;
  padding: 0 32px 30px;
  box-shadow: 0 2px 4px 0 rgba(0,0,0,.05);
  background: #fff;
}
.box .boxinfo h3
{
  font-size: 20px;
  color: #3d3d3d;
  height: 90px;
  line-height: 90px;
  border-bottom: 1px solid #e0e0e0;
}
.box .boxinfo .userinfo
{
  padding-top:20px;
  display: flex;
}
.box .boxinfo .userinfo .info_left
{
  width: 100px;
  text-align: center;
}
.box .boxinfo .userinfo .info_left img
{
  width: 100%;
  border-radius: 50%;
}
.box .boxinfo .userinfo .info_right
{
  margin-left:100px;
  width:calc(100% - 200px);
}
.mr-20
{
  margin-bottom: 20px;
}
.pb-30
{
  padding-bottom: 30px;
}
.box .boxinfo .userinfo .info_right .first
{
  color:#999;
}
.box .boxinfo .userinfo .info_right .second
{
  font-size: 14px;
  border-bottom: 1px solid #e0e0e0;
}
.box .boxinfo .userinfo .info_right .third ul li
{
  list-style: none;
  margin-bottom: 20px;
}
.box .boxinfo .userinfo .info_right .third ul li:first-child
{
  display: flex;
  align-items: center;
  justify-content: space-between;
}
</style>
