import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import App from './App.vue'
import router from './router'
import '@/assets/css/global.css'
import '@/assets/css/manager.css'
import '@/assets/css/homeview.css'
import '@/assets/css/playlist.css'
import '@/assets/css/song.css'


Vue.config.productionTip = false
Vue.use(ElementUI);
new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
