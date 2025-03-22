<template>
    <el-footer style="display: flex; justify-content: center; align-items: center; height: 80px; background-color: #f5f5f5;">
      <div v-if="currentMusicUrl" class="player-container" style="width: 400px; display: flex; align-items: center;">
        <!-- 歌曲封面 -->
        <img :src="currentSong.cover" alt="Cover" style="width: 60px; height: 60px; border-radius: 4px; margin-right: 20px;">

        <!-- 歌曲信息 -->
        <div style="flex-grow: 1;">
          <h4>{{ currentSong.name }}</h4>
          <p>{{ currentSong.artist }}</p>
        </div>

        <!-- 播放控制 -->
        <div class="controls" style="display: flex; justify-content: center; align-items: center;">
          <el-button icon="el-icon-prev" @click="playPrevious"></el-button>
          <el-button :icon="isPlaying ? 'el-icon-pause' : 'el-icon-play'" @click="togglePlay"></el-button>
          <el-button icon="el-icon-next" @click="playNext"></el-button>
        </div>
      </div>
      <audio v-if="currentMusicUrl" ref="audioPlayer" :src="currentMusicUrl" @play="onPlay" @pause="onPause" @ended="onEnd" />
    </el-footer>
  </template>

  <script>
  export default {
    data() {
      return {
        currentMusicUrl: '', // 当前播放的音乐链接
        currentSong: {}, // 当前歌曲信息
        isPlaying: false, // 播放状态
        playlist: [], // 播放列表
        currentIndex: 0 // 当前歌曲在播放列表中的索引
      }
    },
    methods: {
      // 播放歌曲
      togglePlay() {
        const audioPlayer = this.$refs.audioPlayer;
        if (this.isPlaying) {
          audioPlayer.pause();
        } else {
          audioPlayer.play();
        }
      },
      // 播放上一首
      playPrevious() {
        if (this.currentIndex > 0) {
          this.currentIndex--;
        } else {
          this.currentIndex = this.playlist.length - 1;
        }
        this.updateCurrentSong();
      },
      // 播放下一首
      playNext() {
        if (this.currentIndex < this.playlist.length - 1) {
          this.currentIndex++;
        } else {
          this.currentIndex = 0;
        }
        this.updateCurrentSong();
      },
      // 更新当前歌曲
      updateCurrentSong() {
        this.currentSong = this.playlist[this.currentIndex];
        this.currentMusicUrl = this.currentSong.url;
        this.isPlaying = true;
        this.$nextTick(() => {
          this.$refs.audioPlayer.play();
        });
      },
      // 播放开始时触发
      onPlay() {
        this.isPlaying = true;
      },
      // 播放暂停时触发
      onPause() {
        this.isPlaying = false;
      },
      // 播放结束时触发
      onEnd() {
        this.playNext();
      },
      // 初始化播放列表
      setPlaylist(songs) {
        this.playlist = songs;
        this.updateCurrentSong();
      }
    },
    created() {
      const songs = [
        { name: 'Song 1', artist: 'Artist 1', url: 'D:\\毕业设计\\data\\genres_original\\blues\\blues.00000.wav', cover: 'assets/img/avatar.jpeg' },
        { name: 'Song 2', artist: 'Artist 2', url: 'D:\\毕业设计\\data\\genres_original\\blues\\blues.00001.wav', cover: 'assets/img/avatar.jpeg' },
        { name: 'Song 3', artist: 'Artist 3', url: 'D:\\毕业设计\\data\\genres_original\\blues\\blues.00002.wav', cover: 'assets/img/avatar.jpeg' }
      ];
      this.setPlaylist(songs);
    }
  }
  </script>

  <style scoped>
  .player-container {
    display: flex;
    align-items: center;
  }

  .controls {
    display: flex;
    justify-content: space-between;
    width: 120px;
  }

  img {
    border-radius: 5px;
  }

  h4, p {
    margin: 0;
  }

  .el-button {
    padding: 0;
  }
  </style>
