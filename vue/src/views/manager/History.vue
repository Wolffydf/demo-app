<template>
  <div class="history-container">
    <h2>历史记录</h2>
    <el-table :data="historyList" style="width: 100%">
      <el-table-column prop="id" label="ID" width="60"></el-table-column>
<!--      <el-table-column prop="type" label="类型" width="80">-->
<!--        <template v-slot="{ row }">-->
<!--          {{ row.type === 1 ? '歌曲' : '歌单' }}-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column prop="song_name" label="歌曲名称" width="200"></el-table-column>
<!--      <el-table-column prop="playlist_name" label="歌单名称" width="200"></el-table-column>-->
      <el-table-column prop="play_time" label="最近播放时间" width="200"></el-table-column>
      <el-table-column label="操作" width="120">
        <template v-slot="{ row }">
          <el-button type="danger" size="mini" @click="removeHistory(row.id)">删除记录</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-button type="primary" @click="fetchHistories">刷新记录</el-button>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "History",
  data() {
    return {
      historyList: [],
    };
  },
  created() {
    // 获取用户 ID
    const user = JSON.parse(localStorage.getItem("user"));
    this.userId = user.id;
    this.fetchHistories();
  },
  methods: {
    fetchHistories() {
      console.log(this.userId);
      request.get("/history/list",{
        params: {
          userId: this.userId,
        }
      }).then((res) => {
        this.historyList = res;
        console.log(res);
        console.log(typeof res);
      });
    },
    removeHistory(id) {
      request.delete(`/history/delete/${id}`).then(() => {
        this.fetchHistories();
      });
    },
  },
  mounted() {
    this.fetchHistories();
  },
};
</script>

<style scoped>
.history-container {
  padding: 20px;
}
</style>
