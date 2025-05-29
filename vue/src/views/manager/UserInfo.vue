<template>
    <div class="user-info-card">
        <el-card class="user-info">
            <div class="avatar-section">
                <el-avatar :size="100" :src="user.avatar" />
            </div>
            <el-descriptions title="用户信息" :column="1" border v-if="!isEditing">
                <el-descriptions-item label="用户名">{{ user.username }}</el-descriptions-item>
                <!-- <el-descriptions-item label="密码">{{ user.password }}</el-descriptions-item> -->
                <el-descriptions-item label="性别">{{ user.sex === 1 ? '男' : '女' }}</el-descriptions-item>
                <el-descriptions-item label="电话">{{ user.tel }}</el-descriptions-item>
                <el-descriptions-item label="邮箱">{{ user.email }}</el-descriptions-item>
                <el-descriptions-item label="出生日期">{{ user.birth }}</el-descriptions-item>
                <el-descriptions-item label="简介">{{ user.introduction }}</el-descriptions-item>
                <el-descriptions-item label="所在地">{{ user.location }}</el-descriptions-item>
                <el-descriptions-item label="创建时间">{{ user.create_time }}</el-descriptions-item>
                <el-descriptions-item label="更新时间">{{ user.update_time }}</el-descriptions-item>
            </el-descriptions>

            <el-form v-else label-width="100px">
                <el-form-item label="用户名"><el-input v-model="user.username" :disabled="true" /></el-form-item>
                <!-- <el-form-item label="密码"><el-input v-model="user.password" type="password" /></el-form-item> -->
                <el-form-item label="性别"><el-input v-model="user.sex" /></el-form-item>
                <el-form-item label="电话"><el-input v-model="user.tel" /></el-form-item>
                <el-form-item label="邮箱"><el-input v-model="user.email" /></el-form-item>
                <el-form-item label="出生日期"><el-input v-model="user.birth" :disabled="true"/></el-form-item>
                <el-form-item label="简介"><el-input v-model="user.introduction" /></el-form-item>
                <el-form-item label="所在地"><el-input v-model="user.location" /></el-form-item>
            </el-form>

            <div class="button-section">
                <el-button type="primary" @click="toggleEdit">{{ isEditing ? '保存' : '编辑' }}</el-button>
            </div>
        </el-card>
    </div>
</template>

<script>
import request from '@/utils/request';
export default {
    data() {
        return {
            user: JSON.parse(localStorage.getItem("user") || {}),
            isEditing: false,
        };
    },
    created() {
        // this.loadUserInfo();
    },
    methods: {
        loadUserInfo() {
            request.get('/consumer/user_info').then(res => {
                if (res.code === '0') {
                    this.user = res.data;
                } else {
                    this.$notify.error(res.msg);
                }
            });
        },
        updateUserInfo() {
            this.user.update_time = new Date().toISOString().slice(0, 19);

            request.post('/consumer/update_user_info', this.user, {
                headers: {
                    'Content-Type': 'application/json;charset=UTF-8'
                }
            }).then(res => {
                console.log(res.code);
                if (res.code === '0') {
                    this.$notify.success('更新成功');
                    this.isEditing = false;
                    // this.loadUserInfo();
                } else {
                    this.$notify.error(res.msg);
                }
            });
        },
        toggleEdit() {
            if (this.isEditing) {
                this.add();
                this.updateUserInfo();
            } else {
                this.isEditing = true;
            }
        },
        add() {
            console.log(this.user);
            // this.dialogVisible = true;
        },
        save() {
            request.post("/song/upload_song", this.form).then(res => { //post请求把form对象传到后端，后端写逻辑把form存到数据库里
                if (res.code === '0') { //如果接口调用成功，则把模态框关闭，重新加载一下数据库的数据
                    this.$notify.success("新增成功");
                    this.dialogVisible = false;
                    this.load_song();
                } else {
                    this.$notify.error(res.msg); // 如果不成功，返回报错信息
                }
            })

        }
    },
};
</script>

<style scoped>
.user-info-card {
    display: flex;
    justify-content: center;
    padding: 20px;
}

.user-info {
    width: 600px;
}

.avatar-section {
    display: flex;
    justify-content: center;
    margin-bottom: 20px;
}

.button-section {
    margin-top: 20px;
    text-align: center;
}
</style>