<script setup lang="ts">
import dayjs from 'dayjs/esm'

const total = ref(0)

const records = ref<[{ id: number, title: string, stopTime: string }] | undefined>(undefined)

function page(val: number) {
  useApiContestList(val).then((res) => {
    if (res.data.value.status === 200) {
      total.value = res.data.value.content.totalPage
      records.value = res.data.value.content.records

      useWindowScroll().y.value = 0
    }
  })
}

page(1)
</script>

<template>
  <div m-auto max-w-2xl>
    <div v-for="item in records" :key="item.id" mt-5>
      <el-card shadow="hover">
        <div @click="useRouter().push(`/code/contest/${item.id}`)">
          <h1 text-center>
            {{ item.title }}
          </h1>
          <div text-center>
            <el-text v-if="dayjs().isBefore(dayjs(item.stopTime))" type="success">
              结束时间： {{ item.stopTime.replace('T', ' ') }}
            </el-text>
            <el-text v-if="dayjs().isAfter(dayjs(item.stopTime))" type="danger">
              已结束
            </el-text>
          </div>
        </div>
      </el-card>
    </div>
    <div mt-5 flex justify-end>
      <el-pagination v-model:page-count="total" small background layout="prev, pager, next" @update:current-page="page" />
    </div>
  </div>
</template>
