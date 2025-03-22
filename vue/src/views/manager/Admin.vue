<template>
    <div>
        <div style="margin-bottom: 40px;font-size: 18px;font-weight: 530;">管理员管理</div>
        <div style="margin-bottom: 20px; display: flex">
            <div style="flex: 10;text-align: left">
                <el-input placeholder="请输入查询内容" size="small" v-model="input" style="width: 20%; margin-right: 20px"><i
                        slot="suffix" class="el-input__icon el-icon-search"></i></el-input>
            </div>
            <div style="flex:2;text-align: right">
                <el-button type="success" size="small"
                    style="border-radius: 1px;width: 100px;text-align: center">新增</el-button>
            </div>
        </div>
        <div style="width: 100%;height:1000px;padding:40px 40px; background-color: white;border-radius: 4px">
            <el-table :data="tableData" border :fit="true" stripe style="width: 100%"
                :cell-style="{ textAlign: 'center' }" :header-cell-style="{ textAlign: 'center' }">

                <el-table-column prop="id" label=""></el-table-column>
                <el-table-column prop="username" label="姓名"></el-table-column>
                <el-table-column prop="password" label="密码"></el-table-column>
                <el-table-column fixed="right" label="操作">
                    <template slot-scope="scope">
                        <el-button @click="handleClick(scope.row)" type="text" size="small">查看</el-button>
                        <el-button type="text" size="small">编辑</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="block" style="text-align: right;margin-top: 20px">
                <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange"
                    :current-page="currentPage4" :page-sizes="[100, 200, 300, 400]" :page-size="100"
                    layout="prev, pager, next" :total="1000">
                </el-pagination>
            </div>
        </div>

    </div>
</template>
<script>
import request from "@/utils/request";
export default {
    // 页面加载时调用方法
    created(){
    this.load();
    },
    methods: {
        handleClick(row) {
            console.log(row);
        },
        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
        },
        handleCurrentChange(val) {
            console.log(`当前页: ${val}`);
        },
        load() {
            request.get("admin/alldata").then(res => {
                //   如果调接口成了
                if (res.code === '0') {//后端Result中我们定义SUCCESS = ‘0’为调用接口成功
                    this.tableData = res.data;
                } else {//如果不成功，打印出错误信息
                    this.$notify.error(res.msg);//msg是后端定义的属性
                }
            })
        }
    },

    data() {
        return {
            input: '',
            currentPage1: 5,
            currentPage2: 5,
            currentPage3: 5,
            currentPage4: 1,
            tableData: []
        }
    }
}
</script>