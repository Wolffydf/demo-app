<template>
  <div class="collect-container">
    <h2>我的收藏</h2>
    <el-table :data="collectList" style="width: 100%">
      <el-table-column prop="id" label="ID" width="60"></el-table-column>
      <el-table-column prop="type" label="类型" width="80">
        <template v-slot="{ row }">
          {{ row.type === 1 ? '歌曲' : '歌单' }}
        </template>
      </el-table-column>
      <el-table-column prop="song_name" label="歌曲名称" width="200"></el-table-column>
      <el-table-column prop="playlist_name" label="歌单名称" width="200"></el-table-column>
      <el-table-column prop="create_time" label="收藏时间" width="180"></el-table-column>
      <el-table-column label="操作" width="120">
        <template v-slot="{ row }">
          <el-button type="danger" size="mini" @click="removeCollect(row.id)">取消收藏</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-button type="primary" @click="fetchCollects">刷新收藏</el-button>
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
  methods: {
    fetchCollects() {
      request.get("/collect/list").then((res) => {
        this.collectList = res.data;
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
