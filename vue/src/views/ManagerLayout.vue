<template>
  <div>
    <el-container style="height: 500px;">
      <!--      侧边栏-->
      <el-aside class="m-aside" style="width: 250px;">
        <!--        logo和系统名称-->
        <div class="m-sysName">
          <img src="@/assets/img/logo.png" alt="" width="20%">
          <span class="m-nameText">音乐网站</span>
        </div>
        <!--        侧边菜单栏-->
        <el-menu class="el-menu" :default-active="$route.path" router>
          <el-submenu index="1">
            <template slot="title"><i class="el-icon-user"></i>系统首页</template>
            <el-menu-item index="/manager/home">推荐歌曲</el-menu-item>
          </el-submenu>
          <el-submenu index="2">
            <template slot="title"><i class="el-icon-user"></i>管理</template>
            <!-- <el-menu-item index="/manager/admin">管理员管理</el-menu-item>
            <el-menu-item index="/manager/consumer">用户管理</el-menu-item> -->
            <el-menu-item index="/manager/song_manager">音乐后台</el-menu-item>
          </el-submenu>
          <el-submenu index="3">
            <template slot="title"><i class="el-icon-news"></i>我的</template>
            <el-menu-item index="/manager/collect">收藏</el-menu-item>
            <el-menu-item index="/manager/history">历史记录</el-menu-item>
            <el-menu-item index="/manager/user_info">个人信息</el-menu-item>
          </el-submenu>
          <el-submenu index="4">
            <template slot="title"><i class="el-icon-set-up"></i>设置</template>
            <el-menu-item index="3-1">1</el-menu-item>
            <el-menu-item index="3-2">2</el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <!--      右侧-->
      <el-container class="right-container">
        <!--        顶部菜单栏-->
        <el-header class="top-header">
          <img src="@/assets/img/avatar.jpeg" alt="" style="width: 40px;border-radius: 50%;margin-right: 10px;">
          <span style="color: #4c5a73;font-weight: bold;font-size: 14px;margin-right: 20px">欢迎您，{{ user.username
            }}</span>
          <el-dropdown>
            <i class="el-icon-setting" style="margin-right: 15px"></i>
            <el-dropdown-menu slot="dropdown">
              <div @click="goToUserInfo()"><el-dropdown-item >个人中心</el-dropdown-item></div> 
              <div @click="logout()"><el-dropdown-item>退出登录</el-dropdown-item></div>
            </el-dropdown-menu>
          </el-dropdown>

        </el-header>
        <!--表单-->
        <el-main>
          <div>
            <router-view />
          </div>
        </el-main>

      </el-container>
    </el-container>
    <MusicPlayer v-if="showMusicPlayer" />
  </div>
</template>

<script>
import request from "@/utils/request";
import MusicPlayer from "@/views/MusicPlayer.vue";

export default {
  name: "ManagerLayout",
  components: {
    MusicPlayer,
  },
  data() {
    return {
      // 定义User对象，从缓存里取值
      user: JSON.parse(localStorage.getItem("user") || {}),
    }
  },

  computed: {
    showMusicPlayer() {
      // 只在 /manager/home 页面显示音乐播放器
      return this.$route.path === "/manager/home";
    }
  },


  methods: {
    // 退出登录方法
    logout() {
      localStorage.removeItem("user");
      this.$router.push("/login");
    },
    goToUserInfo() {
      this.$router.push('/manager/user_info'); 
    }
  }

}
</script>