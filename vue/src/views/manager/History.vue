<template>
  <div class="collect-container">
    <h2>历史记录</h2>
    <el-table :data="collectList" style="width: 100%">
      <el-table-column prop="id" label="ID" width="60"></el-table-column>
      <el-table-column prop="type" label="类型" width="80">
        <template v-slot="{ row }">
          {{ row.type === 1 ? '歌曲' : '歌单' }}
        </template>
      </el-table-column>
      <el-table-column prop="song_name" label="歌曲名称" width="200"></el-table-column>
      <el-table-column prop="playlist_name" label="歌单名称" width="200"></el-table-column>
      <el-table-column prop="create_time" label="播放时间" width="180"></el-table-column>
      <el-table-column label="操作" width="120">
        <template v-slot="{ row }">
          <el-button type="danger" size="mini" @click="removeCollect(row.id)">删除记录</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-button type="primary" @click="fetchCollects">刷新记录</el-button>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Collect",
  data() {
    return {
      collectList: [],
    };
  },
  created() {
    // 获取用户 ID
    const user = JSON.parse(localStorage.getItem("user"));
    this.userId = user.id;
    this.fetchCollects();
  },
  methods: {
    fetchCollects() {
      console.log(this.userId);
      request.get("/collect/list",{
        params: {
          userId: this.userId,
        }
      }).then((res) => {
        this.collectList = res;
        console.log(res);
        console.log(typeof res);
      });
    },
    removeCollect(id) {
      request.delete(`/collect/delete/${id}`).then(() => {
        this.fetchCollects();
      });
    },
  },
  mounted() {
    this.fetchCollects();
  },
};
</script>

<style scoped>
.collect-container {
  padding: 20px;
}
</style>
