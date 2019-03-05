import Vuex from 'vuex'
import Vue from 'vue'

Vue.use(Vuex)


const state = {
    disabled:true,
    search:''
}

const mutations = {
    setSearch(state,val){
       state.search = val
    }
}

const actions = {

}


export default new Vuex.Store({
  state,
  mutations,
  actions
})
