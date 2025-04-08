<template>
    <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="歌曲管理" name="first">
            <div class="SearchMusicFlex">
                <span></span>
                <el-input v-model="input1" style="width: 240px; height: 50px; border: none;" placeholder="按歌曲名搜索">
                    <template #suffix>
                        <el-row>
                            <el-button icon="el-icon-search" circle style="border: none;margin-top: 1px;"
                                @click="search_music"></el-button>
                        </el-row>
                    </template>
                </el-input>
                <el-button type="success" size="small"
                    style="border-radius: 1px;width: 100px;text-align: center;margin-left: 12px;"
                    @click="add">新增</el-button>

            </div>
            <div style="padding: 40px; background-color: white; border-radius: 4px;">
                <el-table :data="tableData" border stripe style="width: 100%">
                    <el-table-column prop="id" label="ID"></el-table-column>
                    <el-table-column prop="name" label="歌名"></el-table-column>
                    <el-table-column prop="singer_name" label="歌手"></el-table-column>
                    <el-table-column prop="genre" label="流派"></el-table-column>
                    <el-table-column prop="pic" label="封面"></el-table-column>
                    <el-table-column fixed="right" label="操作">
                        <template #default="scope">
                            <el-button @click="handleClick(scope.row)" type="text" size="small">播放</el-button>
                            <el-button type="text" size="small">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <div class="block" style="text-align: right;margin-top: 20px">
                    <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange"
                        :current-page="currentPage4" :page-sizes="[100, 200, 300, 400]" :page-size="pageSize"
                        layout="prev, pager, next" :total="total">
                    </el-pagination>
                </div>

            </div>
            <el-dialog title="请填写信息" :visible.sync="dialogVisible" width="40%">
                <el-form :model="form" label-position="right" label-width="100px" style="padding-right: 40px">
                    <el-form-item label="歌名">
                        <el-input size="small" v-model="form.name" placeholder="请输入歌名名"></el-input>
                    </el-form-item>
                    <el-form-item label="歌手">
                        <el-input size="small" v-model="form.singer_name" placeholder="请输入歌手"></el-input>
                    </el-form-item>
                    <el-form-item label="url">
                        <el-input size="small" v-model="form.url" placeholder="请输入歌曲路径"></el-input>
                    </el-form-item>
                    <el-form-item label="简介">
                        <el-input size="small" v-model="form.introduction" placeholder="请输入简介"></el-input>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button size="small" @click="dialogVisible = false">取 消</el-button>
                    <el-button size="small" type="primary" @click="save">保 存</el-button>
                </div>
            </el-dialog>


        </el-tab-pane>
        <el-tab-pane label="歌单管理" name="second">
            <div class="SearchMusicFlex">
                <span></span>
                <el-input v-model="input2" style="width: 240px; height: 50px; border: none;" placeholder="按歌单名搜索">
                    <template #suffix>
                        <el-row>
                            <el-button icon="el-icon-search" circle style="border: none;margin-top: 1px;"
                                @click="search_playlist"></el-button>
                        </el-row>
                    </template>
                </el-input>
            </div>
            <div style="padding: 40px; background-color: white; border-radius: 4px;">
                <el-table :data="tableData" border stripe style="width: 100%">
                    <el-table-column prop="id" label="ID"></el-table-column>
                    <el-table-column prop="title" label="标题"></el-table-column>
                    <el-table-column prop="style" label="风格"></el-table-column>
                    <el-table-column prop="introduction" label="简介"></el-table-column>
                    <el-table-column prop="pic" label="封面"></el-table-column>
                    <el-table-column fixed="right" label="操作">
                        <template #default="scope">
                            <el-button @click="handleClick(scope.row)" type="text" size="small">播放</el-button>
                            <el-button type="text" size="small">收藏</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <div class="block" style="text-align: right;margin-top: 20px">
                    <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange"
                        :current-page="currentPage4" :page-sizes="[100, 200, 300, 400]" :page-size="pageSize"
                        layout="prev, pager, next" :total="total">
                    </el-pagination>
                </div>
            </div>

        </el-tab-pane>
    </el-tabs>
</template>

<script>
import request from "@/utils/request";
export default {
    created() {
        this.load_song();
    },

    data() {
        return {
            form: {},
            dialogVisible: false,
            input1: '',
            input2: '',
            currentPage1: 5,
            currentPage2: 5,
            currentPage3: 5,
            currentPage4: 1,
            pageSize: 10, // 每页显示条数
            total: 0, // 数据总条数
            allData: [], // 全部数据
            tableData: [], // 当前页显示的数据
            showSearchResult: false,
            activeName: 'first'
        };
    },
    methods: {
        handleClick(tab, event) {
            this.load(tab.name);
            console.log(tab, event);

        },
        load(tabName) {
            if (tabName === 'first') {
                this.load_song();
            } else if (tabName === 'second') {
                this.load_playlist();
            }
            // 可以根据需要添加更多条件
        },
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
                } else {
                    this.$notify.error(res.msg);
                }
            }).catch(err => {
                console.error('搜索失败', err);
                this.$notify.error('搜索请求失败');
            });

        },
        search_playlist() {
            if (!this.input2.trim()) {
                this.$notify.error('请输入歌单名');
                return;
            }
            request.get('/playlist/search', {
                params: { name: this.input2 }
            }).then(res => {
                if (res.code === '0') {
                    this.allData = res.data;
                    this.total = res.data.length; // 总数
                    this.updateTableData();
                } else {
                    this.$notify.error(res.msg);
                }
            }).catch(err => {
                console.error('搜索失败', err);
                this.$notify.error('搜索请求失败');
            });

        },
        load_song() {
            request.get("song/alldata").then(res => {
                //   如果调接口成了
                if (res.code === '0') {//后端Result中我们定义SUCCESS = ‘0’为调用接口成功
                    this.tableData = res.data;
                    this.allData = res.data;
                    this.total = res.data.length; // 总数
                    this.updateTableData();
                } else {//如果不成功，打印出错误信息
                    this.$notify.error(res.msg);//msg是后端定义的属性
                }
            })
        },
        load_playlist() {
            request.get("playlist/alldata").then(res => {
                //   如果调接口成了
                if (res.code === '0') {//后端Result中我们定义SUCCESS = ‘0’为调用接口成功
                    this.tableData = res.data;
                    this.allData = res.data;
                    this.total = res.data.length; // 总数
                    this.updateTableData();
                } else {//如果不成功，打印出错误信息
                    this.$notify.error(res.msg);//msg是后端定义的属性
                }
            })
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
        updateTableData() {
            const start = (this.currentPage4 - 1) * this.pageSize;
            const end = start + this.pageSize;
            this.tableData = this.allData.slice(start, end);
        },
        // 点击新增按钮点击事件调用
        add() {
            this.form = {};
            this.dialogVisible = true;
        },
        save() {
            if (!this.form.id) { //如果没有id 走新增接口
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
        }
    }
};
</script>
