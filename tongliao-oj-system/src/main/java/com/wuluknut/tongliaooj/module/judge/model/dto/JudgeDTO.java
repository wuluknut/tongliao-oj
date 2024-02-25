/*
 * Copyright (C) 2024 Wulu Knut
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.wuluknut.tongliaooj.module.judge.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * 代码执行数据传输类
 *
 * @author <a href="mailto:wuluknut@gmail.com" rel="nofollow">wuluknut</a>
 * @version 0.0.0
 */
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
public class JudgeDTO {

    /**
     * 代码执行状态
     *
     * <table>
     * <tr><td>1</td><td>In Queue</td><td>排队中，等待处理</td></tr>
     * <tr><td>2</td><td>Processing</td><td>处理中，正在执行和测试</td></tr>
     * <tr><td>3</td><td>Accepted</td><td>通过，提交正确</td></tr>
     * <tr><td>4</td><td>Wrong Answer</td><td>答案错误，输出不正确</td></tr>
     * <tr><td>5</td><td>Time Limit Exceeded</td><td>超出时间限制，执行时间过长</td></tr>
     * <tr><td>6</td><td>Compilation Error</td><td>编译错误，代码未能编译</td></tr>
     * <tr><td>7</td><td>Runtime Error (SIGSEGV)</td><td>运行时错误，段错误</td></tr>
     * <tr><td>8</td><td>Runtime Error (SIGXFSZ)</td><td>运行时错误，超出文件大小限制</td></tr>
     * <tr><td>9</td><td>Runtime Error (SIGFPE)</td><td>运行时错误，浮点异常</td></tr>
     * <tr><td>10</td><td>Runtime Error (SIGABRT)</td><td>运行时错误，程序被终止</td></tr>
     * <tr><td>11</td><td>Runtime Error (NZEC)</td><td>运行时错误，非零退出代码</td></tr>
     * <tr><td>12</td><td>Runtime Error (Other)</td><td>运行时错误，其他未指定错误</td></tr>
     * <tr><td>13</td><td>Internal Error</td><td>内部错误，裁判系统处理时出错</td></tr>
     * <tr><td>14</td><td>Exec Format Error</td><td>执行格式错误，无法执行</td></tr>
     * </table>
     */
    @JsonProperty("status")
    private JudgeStatus status;

    /**
     * 代码执行语言 ID
     *
     * <table>
     * <tr><td>54</td><td>C++</td><td>GCC 9.2.0</td></tr>
     * <tr><td>62</td><td>Java</td><td>OpenJDK 13.0.1</td></tr>
     * <tr><td>63</td><td>JavaScript</td><td>Node.js 12.14.0</td></tr>
     * <tr><td>71</td><td>Python</td><td>Python 3.8.1</td></tr>
     * <tr><td>82</td><td>SQL</td><td>SQLite 3.27.2</td></tr>
     * </table>
     */
    @JsonProperty("language_id")
    private Integer lang;

    /**
     * 代码执行源码
     */
    @JsonProperty("source_code")
    private String code;

    /**
     * 代码执行输入
     */
    @JsonProperty("stdin")
    private String input;

    /**
     * 代码执行输出
     */
    @JsonProperty("stdout")
    private String output;

    /**
     * 代码执行错误
     */
    @JsonProperty("stderr")
    private String error;

    /**
     * 代码执行令牌
     */
    @JsonProperty("token")
    private String token;

    /**
     * 代码执行耗时(秒)
     */
    @JsonProperty("time")
    private String time;

    /**
     * 代码执行内存占用(KB)
     */
    @JsonProperty("memory")
    private String memory;

    @Getter
    @Setter
    @ToString
    @SuperBuilder
    @NoArgsConstructor
    public static class JudgeStatus {

        /**
         * 代码执行状态 ID
         */
        @JsonProperty("id")
        private Integer id;

        /**
         * 代码执行状态摘要
         */
        @JsonProperty("description")
        private String desc;
    }
}
