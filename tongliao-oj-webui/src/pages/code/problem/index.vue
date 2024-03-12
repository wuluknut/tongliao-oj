<script setup lang="ts">
const total = ref(0)

const keyword = ref('')

const tableData = ref([])

function page(val: number) {
  useApiProblemList(val, keyword.value).then((res) => {
    if (res.data.value.status === 200) {
      total.value = res.data.value.content.totalPage
      tableData.value = res.data.value.content.records

      useWindowScroll().y.value = 0
    }
  })
}

page(1)
</script>

<template>
  <div m-auto max-w-4xl>
    <div flex justify-end>
      <div>
        <el-input v-model="keyword" placeholder="搜索">
          <template #append>
            <el-button @click="page(1)">
              <i class="i-ep-search" />
            </el-button>
          </template>
        </el-input>
      </div>
    </div>
    <div mt-5>
      <client-only>
        <el-table :data="tableData" stripe border>
          <el-table-column label="题目">
            <template #default="scope">
              <el-link :underline="false" @click="useRouter().push(`/code/problem/${scope.row.id}`)">
                {{ scope.row.title }}
              </el-link>
            </template>
          </el-table-column>
          <el-table-column label="语言" width="150" align="center">
            <template #default="scope">
              <div v-if="scope.row.language === 54">
                <el-tag type="info">
                  C++
                </el-tag>
              </div>
              <div v-if="scope.row.language === 62">
                <el-tag type="success">
                  Java
                </el-tag>
              </div>
              <div v-if="scope.row.language === 63">
                <el-tag type="warning">
                  JavaScript
                </el-tag>
              </div>
              <div v-if="scope.row.language === 71">
                <el-tag type="primary">
                  Python
                </el-tag>
              </div>
              <div v-if="scope.row.language === 82">
                <el-tag type="danger">
                  SQL
                </el-tag>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="难度" width="300" align="center">
            <template #default="scope">
              <el-rate v-model="scope.row.rate" :max="10" allow-half disabled />
            </template>
          </el-table-column>
        </el-table>
      </client-only>
    </div>
    <div mt-5 flex justify-end>
      <el-pagination v-model:page-count="total" small background layout="prev, pager, next" @update:current-page="page" />
    </div>
  </div>
</template>
