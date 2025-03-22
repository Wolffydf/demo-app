<template>
  <div class="home">
    <el-container>
      <el-header>
        <div class="SearchMusicFlex">
          <!-- <span></span> -->
          <!-- <el-input v-model="input1" style="width: 240px" placeholder="搜索歌曲">
            <template #suffix>
              <el-row>
                <el-button icon="el-icon-search" circle style="border: none;" @click="search_music"></el-button>
              </el-row>
            </template>
</el-input> -->
          <div class="search">
            <input v-model="input1" placeholder="Search..." type="text">
            <button type="submit" @click="search_music">Go</button>
          </div>

        </div>
      </el-header>



      <el-main style="background: linear-gradient(to right, #c6e3ff, #f7f7f7);">
        <!-- div1：主页 -->
        <div v-if="!showSearchResult"
          style="padding: 40px; background: linear-gradient(to right, #c6e3ff, #f7f7f7); border-radius: 4px;">
          <!-- <h2>主页面内容，推荐歌单和热门歌曲展示</h2> -->

          <!-- 推荐歌单区 -->
          <div class="song-list-section">
            <h3>推荐歌单</h3>
            <div class="playlist-grid">
              <el-card v-for="playlist in Playlists" :key="playlist.id" class="playlist-card"
                :style="{ backgroundImage: `url(${playlist.pic})` }">
                <!-- <img :src="playlist.pic" alt="歌单封面" class="playlist-cover" /> -->
                <div class="playlist-info">
                  <p class="playlist-title">{{ playlist.title }}</p>
                  <p class="playlist-introduction">{{ playlist.introduction }}</p>
                  <div v-if="1">
                    <p v-for="song in playlist.songs" :key="song.id" class="playlist-song" @click="openModal(song)">

                      {{ song.name }}
                    </p>
                  </div>

                </div>
              </el-card>
            </div>

          </div>

          <!-- 热门歌曲区 -->
          <div class="hot-songs-section" style="margin-top: 40px;">
            <h3>热门歌曲</h3>
            <div class="song-grid">
              <div v-for="song in HotSongs" :key="song.id" class="song-card">
                <div class="bg"></div>
                <div class="blob"></div>
                <!-- 添加歌曲信息 -->
                <div class="song-cover-wrapper">
                  <img :src="song.pic" alt="歌曲封面" class="song-cover" />
                </div>
                <div class="song-info">
                  <p class="song-name">{{ song.name }}</p>
                  <p class="singer-name">{{ song.singer_name }}</p>
                </div>

              </div>
            </div>
          </div>



          <!-- 最新发布的歌曲 -->
          <div class="new-songs-section" style="margin-top: 40px;">
            <h3>最新发布的歌曲</h3>
            <el-row :gutter="20">
              <el-col :span="6" v-for="(song, index) in newSongs" :key="index">
                <el-card :body-style="{ padding: '20px' }" class="song-card">
                  <img :src="song.cover" class="song-cover" alt="歌曲封面">
                  <div class="song-info">
                    <h4>{{ song.name }}</h4>
                    <p>{{ song.singer }}</p>
                    <el-button type="primary" @click="playSong(song)">播放</el-button>
                  </div>
                </el-card>
              </el-col>
            </el-row>
          </div>
          <!-- 弹框内容 -->
          <div v-if="isModalOpen" class="modal" @click.self="closeModal">
            <div class="modal-content">
              <h2>{{ currentSong.name }}</h2>
              <p><strong>Artist:</strong> {{ currentSong.singer_name }}</p>
              <p><strong>Genre:</strong> {{ currentSong.genre }}</p>
              <p><strong>Description:</strong> {{ currentSong.introduction }}</p>
              <button class="button-play" @click="playModal">播放</button>
              <button class="button-close" @click="closeModal">关闭</button>
            </div>
          </div>
        </div>

        <!-- div2：搜索结果页面 -->
        <div v-if="showSearchResult" style="padding: 40px; background-color: white; border-radius: 4px;">
          <el-table :data="tableData" border stripe style="width: 100%">
            <el-table-column prop="id" label="ID"></el-table-column>
            <el-table-column prop="name" label="歌名"></el-table-column>
            <el-table-column prop="singer_name" label="歌手"></el-table-column>
            <el-table-column prop="genre" label="流派"></el-table-column>
            <el-table-column prop="pic" label="封面"></el-table-column>
            <el-table-column fixed="right" label="操作">
              <template #default="scope">
                <el-button @click="handleClick(scope.row)" type="text" size="small">播放</el-button>
                <el-button type="text" size="small">收藏</el-button>
              </template>
            </el-table-column>
          </el-table>
          <p v-if="tableData.length === 0" style="text-align: center; margin-top: 20px;">暂无搜索结果</p>
          <div class="block" style="text-align: right;margin-top: 20px">
            <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange"
              :current-page="currentPage4" :page-sizes="[100, 200, 300, 400]" :page-size="100"
              layout="prev, pager, next" :total="1000">
            </el-pagination>
          </div>
        </div>
      </el-main>
      <el-footer class="player-footer">
        <div class="player-container">
          <!-- 封面 -->
          <img :src="song.pic" alt="封面" class="cover-img" />

          <!-- 歌曲信息 -->
          <div class="song-info">
            <h3>{{ song.name }}</h3>
            <p>{{ song.singer_name }}</p>
          </div>

          <!-- 播放 / 暂停按钮 -->
          <button @click="togglePlay">{{ isPlaying ? "暂停" : "播放" }}</button>

          <!-- 进度条 -->
          <input type="range" min="0" :max="duration" v-model="currentTime" @input="seekAudio" />

          <!-- 时间显示 -->
          <span>{{ formatTime(currentTime) }} / {{ formatTime(duration) }}</span>
        </div>

        <!-- 隐藏的音频元素 -->
        <audio ref="audioPlayer" :src="audioUrl" @timeupdate="updateTime" @loadedmetadata="loadMetadata"></audio>
      </el-footer>
    </el-container>
  </div>
</template>


<script>
import request from "@/utils/request";
import axios from 'axios';

export default {
  name: "SearchMusicFlex",
  data() {
    return {
      input1: '',
      currentPage1: 5,
      currentPage2: 5,
      currentPage3: 5,
      currentPage4: 1,
      isModalOpen: false, // 弹框的打开状态
      currentSong: {},   // 当前选中的歌曲信息
      pageSize: 10, // 每页显示条数
      total: 0, // 数据总条数
      allData: [], // 全部数据
      tableData: [], // 当前页显示的数据
      showSearchResult: false,
      audioUrl: "",
      Playlists: [],
      HotSongs: [
        {
          id: 1,
          name: '晴天',
          singer_name: '周杰伦',
          pic: 'https://example.com/qingtian.jpg'
        },
        {
          id: 2,
          name: '告白气球',
          singer_name: '周杰伦',
          pic: 'https://example.com/gaobaiqiqiu.jpg'
        },
        {
          id: 3,
          name: '稻香',
          singer_name: '周杰伦',
          pic: 'https://example.com/daoxiang.jpg'
        }
      ],

      // 音频播放器相关数据
      song: {
        id: "",
        name: "", // 歌曲名称
        singer_name: "",  // 歌手
        pic: require('@/assets/img/record.png'), // 封面图片
        url: "", // 音乐文件
      },
      isPlaying: false,
      currentTime: 0,
      duration: 0,
    };
  },
  created() {
    this.fetchRecommendedPlaylists();
  },
  methods: {
    search_music() {
      if (!this.input1.trim()) {
        this.$notify.error('请输入歌曲名');
        return;
      }
      request.get('/song/search', {
        params: { name: this.input1 }
      }).then(res => {
        if (res.code === '0') {
          this.allData = res.data;
          this.total = res.data.length; // 总数
          this.updateTableData();
          this.showSearchResult = true;
        } else {
          this.$notify.error(res.msg);
        }
      }).catch(err => {
        console.error('搜索失败', err);
        this.$notify.error('搜索请求失败');
      });
    },
    updateTableData() {
      const start = (this.currentPage4 - 1) * this.pageSize;
      const end = start + this.pageSize;
      this.tableData = this.allData.slice(start, end);
    },
    handleClick(row) {
      console.log('播放歌曲:', row);
    },
    navRegister() {
      this.$router.push("/register");
    },
    handleSizeChange(val) {
      this.pageSize = val;
      this.currentPage4 = 1; // 每页数量变了，回到第一页
      this.updateTableData();
    },
    handleCurrentChange(val) {
      this.currentPage4 = val;
      this.updateTableData();
    },
    handleClickSongInfo(song) {
      console.log('Song clicked:', song.name);
    },
    openModal(song) {
      this.currentSong = null; // 先清空
      this.$forceUpdate();
      this.currentSong = { ...song };
      console.log(this.currentSong);
      this.isModalOpen = true;
    },
    closeModal() {
      this.isModalOpen = false;
      this.currentSong = null;
    },
    playModal() {
      console.log('Song clicked:', this.currentSong);
      this.song.id = this.currentSong.id;
      this.song.url = this.currentSong.url;
      this.song.name = this.currentSong.name;
      this.song.singer_name = this.currentSong.singer_name;
      // this.song.pic = this.currentSong.pic;
      console.log(this.song.pic);
      this.audioUrl = `http://localhost:8090/audio/${this.currentSong.id}`;
      this.togglePlay();
    },
    playSound() {
      // 播放逻辑
    },

    async fetchRecommendedPlaylists() {
      const playlistIds = [2, 3, 8, 10];
      try {
        const response = await axios.post('http://localhost:8090/playlist/get_playlists', playlistIds, {
          headers: {
            'Content-Type': 'application/json'
          }
        });

        // 处理数据，为每个歌单的pic字段替换成本地图片路径
        this.Playlists = await Promise.all(response.data.map(async (item) => {
          const images = require.context('@/assets/img/playlist_bg', false, /\.(jpg|jpeg|png)$/);

          // 动态拼接图片路径
          const picPath = images(`./${item.pic}`);

          // 先更新每个歌单的 pic 字段
          const playlist = {
            ...item,
            pic: picPath,
          };

          // 获取该歌单的随机歌曲并将其加到 playlist 对象中
          const songs = await this.fetchRandomSongs(playlist.id);
          console.log(songs);
          return {
            ...playlist,
            songs: songs, // 将获取到的歌曲数据添加到当前歌单
          };
        }));
      } catch (error) {
        console.error('获取推荐歌单失败:', error);
      }
    },
    async fetchRandomSongs(playlistId) {
      try {
        const response = await axios.get(`http://localhost:8090/playlist/randomSongs?playlistId=${playlistId}`);
        return response.data; // 返回获取到的歌曲数据
      } catch (error) {
        console.error('获取随机歌曲失败:', error);
        return []; // 返回空数组作为默认值
      }
    },

    // 音频播放器相关方法
    togglePlay() {
      const audioPlayer = this.$refs.audioPlayer;
      console.log(audioPlayer);
      // const audioPlayer = this.currentSong;
      if (audioPlayer) {
        if (this.isPlaying) {
          audioPlayer.pause();
        }
        else audioPlayer.play();
        this.isPlaying = !this.isPlaying;
      }
    },
    updateTime() {
      const audioPlayer = this.$refs.audioPlayer;
      if (audioPlayer) {
        this.currentTime = audioPlayer.currentTime;
      }
    },
    loadMetadata() {
      const audioPlayer = this.$refs.audioPlayer;
      if (audioPlayer) {
        this.duration = audioPlayer.duration;
      }
    },
    seekAudio() {
      const audioPlayer = this.$refs.audioPlayer;
      if (audioPlayer) {
        audioPlayer.currentTime = this.currentTime;
      }
    },
    formatTime(seconds) {
      const min = Math.floor(seconds / 60);
      const sec = Math.floor(seconds % 60);
      return `${min}:${sec < 10 ? "0" + sec : sec}`;
    },
  },
  mounted() {
    // 组件挂载时加载音频
    const audioPlayer = this.$refs.audioPlayer;
    if (audioPlayer) {
      audioPlayer.load();
    }
  },
};
</script>

<style scoped>
/* 固定底部播放器 */
.player-footer {
  position: fixed;
  bottom: 0;
  left: 0;
  width: 100%;
  background: #222;
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 10px;
  z-index: 10000;
}

/* 播放器内容 */
.player-container {
  display: flex;
  align-items: center;
  gap: 15px;
}

/* 歌曲封面 */
.cover-img {
  width: 50px;
  height: 50px;
  border-radius: 8px;
}

/* 歌曲信息 */
.song-info {
  display: flex;
  flex-direction: column;
}

.song-info h3 {
  margin: 0;
  font-size: 16px;
}

/* .song-info p {
  margin: 0;
  font-size: 12px;
  color: #bbb;
} */

/* 进度条 */
input[type="range"] {
  width: 200px;
}
</style>