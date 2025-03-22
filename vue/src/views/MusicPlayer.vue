<template>
  <el-footer class="player-footer">
    <div class="player-container">
      <!-- 歌曲信息 -->
      <div class="song-info">
        <h3>{{ song.name }}</h3>
        <p>{{ song.singer_name }}</p>
      </div>
      <div class="controls">
        <!-- 封面 -->
        <img :src="song.pic" alt="封面" class="cover-img" :style="{ transform: 'rotate(' + rotation + 'deg)' }"/>

        <!-- 播放 / 暂停按钮 -->
        <button @click="togglePlay" class="play-button">
          <img :src="isPlaying ? require('@/assets/img/pause.png') : require('@/assets/img/play.png')" alt="播放/暂停"
               class="button-icon"
          />
        </button>
        <!-- 进度条 -->
        <input class="progress-bar" type="range" min="0" :max="duration" v-model="currentTime" @input="seekAudio"/>
        <!-- 时间显示 -->
        <span class="time-font">{{ formatTime(currentTime) }} / {{ formatTime(duration) }}</span>
      </div>


    </div>
    <!-- 隐藏的音频元素 -->
    <audio ref="audioPlayer" :src="audioUrl" @timeupdate="updateTime" @loadedmetadata="loadMetadata"></audio>
  </el-footer>
</template>

<script>
import request from "@/utils/request";
import axios from 'axios';
import {EventBus} from "@/assets/js/eventBus";

export default {

  name: "MusicPlayer",
  data() {
    return {
      currentSong: {},   // 当前选中的歌曲信息
      audioUrl: "",
      Playlists: [],
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
      rotation: 0,
    };
  },
  created() {

  },
  methods: {
    updateSong(newSong) {
      console.log("更新歌曲:", newSong);
      this.song = {...newSong}; // 更新歌曲信息
      this.audioUrl = `http://localhost:8090/audio/${newSong.id}`;

      // 确保对象存在
      this.$nextTick(() => {
        if (this.$refs.audioPlayer) {
          this.startRotation();
          this.$refs.audioPlayer.play();
          this.isPlaying = true;
        } else {
          console.error("音频组件未加载，无法播放");
        }
      });
    },
    // 音频播放器相关方法
    togglePlay() {
      const audioPlayer = this.$refs.audioPlayer;
      console.log(audioPlayer);
      // const audioPlayer = this.currentSong;
      if (audioPlayer) {
        if (this.isPlaying) {
          this.stopRotation();
          audioPlayer.pause();
        } else {
          this.startRotation();
          audioPlayer.play();
        }
        this.isPlaying = !this.isPlaying;
      }
    },
    // defaultPlay() {
    //   const audioPlayer = this.$refs.audioPlayer;
    //   audioPlayer.play();
    //   // this.isPlaying = true;
    // },

    // 开始旋转
    startRotation() {
      // 每 100ms 更新一次旋转角度
      this.rotationInterval = setInterval(() => {
        this.rotation += 1;  // 每次旋转1度
      }, 100);
    },

    // 停止旋转
    stopRotation() {
      clearInterval(this.rotationInterval);  // 停止定时器，保持当前角度
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
    // 监听事件总线
    EventBus.$on("update-song", this.updateSong);
    // EventBus.$on("update-song", (newSong) => {
    //   this.song = newSong;
    //   this.audioUrl = newSong.url;
    //   console.log(this.isPlaying);
    //   this.isPlaying = true;
    //   this.defaultPlay();
    // });
    // // 组件挂载时加载音频
    // const audioPlayer = this.$refs.audioPlayer;
    // if (audioPlayer) {
    //   audioPlayer.load();
    // }
  },
};
</script>

<style scoped>
/* 固定底部播放器 */
.player-footer {
  position: fixed;
  bottom: 20px; /* 离下方20px */
  left: 20px; /* 离左边20px */
  right: 20px; /* 离右边20px */
  width: auto;
  background: rgba(255, 255, 255, 0.1); /* 背景透明度设置为 0.1 */
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 35px 20px;
  z-index: 10000;
  border-radius: 20px; /* 圆角效果 */
  backdrop-filter: blur(10px); /* 毛玻璃效果，调整模糊度 */
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.3); /* 添加阴影效果，使其更突出 */
}

/* 播放器内容 */
.player-container {
  display: flex;
  align-items: center;
  gap: 15px;
}

.controls {
  margin-left: auto; /* 将其他内容向右对齐 */
  display: flex;
  align-items: center;
}

.controls > * {
  margin-left: 10px;
}

/* 歌曲封面 */
.cover-img {
  width: 50px;
  height: 50px;
  border-radius: 8px;
  transition: transform 0.2s ease-in-out;
}

/* 歌曲信息 */
.song-info {
  display: flex;
  flex-direction: column;
  max-width: 80%; /* 限制最大宽度，防止超出边框 */
  overflow: hidden; /* 隐藏溢出的内容 */
  text-overflow: ellipsis; /* 超出文本部分显示省略号 */
  white-space: nowrap; /* 保证文本不换行 */
}

/* 歌曲标题 */
.song-info h3 {
  margin: 0;
  font-size: 20px;
  /*text-overflow: ellipsis;*/
  white-space: nowrap; /* 保证标题不换行 */
  width: 400px;
  /*
  animation: scroll-text 10s linear infinite;
  */
}


/* 滚动效果 */
@keyframes scroll-text {
  0% {
    transform: translateX(50%);
  }
  100% {
    transform: translateX(-50%);
  }
}

/* 歌手信息 */
.song-info p {
  margin: 0;
  font-size: 14px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.progress-bar {
  display: block;
  --height-of-loader: 23px;
  --loader-color: #0071e2;
  width: 130px;
  height: var(--height-of-loader);
  border-radius: 30px;
  background-color: rgba(0, 0, 0, 0.2);
  position: relative;
}

.progress-bar::-webkit-slider-thumb {
  appearance: none;
  border-radius: 50%;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.5); /* 设置滑块的阴影 */
}

/*
.progress-bar::before {
  content: "";
  position: absolute;
  background: var(--loader-color);
  top: 0;
  left: 0;
  width: 0%;
  height: 100%;
  border-radius: 30px;
  animation: moving 1s ease-in-out infinite;
}
*/

/* 进度条 */
input[type="range"] {
  width: 200px;
}

.time-font {
  font-size: 14px;
  font-weight: bold;
  color: #034c9f;
}

.play-button {
  background: none;    /* 去除背景色 */
  border: none;        /* 去除边框 */
  padding: 8px;          /* 去除内边距 */
  cursor: pointer;     /* 设置鼠标指针为点击状态 */
}
.button-icon {
  width: 25px;
  height: 25px;
}
</style>