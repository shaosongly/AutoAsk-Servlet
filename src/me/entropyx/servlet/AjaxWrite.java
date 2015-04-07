/**
 * Created by shaosong on 14/12/19.
 */
package me.entropyx.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import me.entropyx.errorhandler.QuestionQueryException;
import me.entropyx.question.QuestionProcessing;
import me.entropyx.question.ResultProcessing;

public class AjaxWrite extends javax.servlet.http.HttpServlet {
    private static final long serialVersionUID = 1L;
    private QuestionProcessing qs;
    private ResultProcessing rs;
    private Map<String,ArrayList<String>> params;
    private Map<String,ArrayList<String>> querys;
    private Map<String,ArrayList<String>> results;

    public void init() throws javax.servlet.ServletException {
        //配置文件路径
        String configFilePath = getServletContext().getRealPath("/")+"/config_files/correspondence.txt";
        String unitFilePath = getServletContext().getRealPath("/")+"/config_files/unit.txt";
        //初始化问题处理模块
        qs = new QuestionProcessing(configFilePath);
        rs = new ResultProcessing(unitFilePath);
        System.out.println("初始化完成");
    }


    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request,response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        System.out.println("receive request");

        //问题示例
        //String question="品牌:宝马,品牌:奔驰,级别:中型车,车身结构:三厢车,进气形式:涡轮增压";
        String question = request.getParameter("question");//获取问题
        String ans;
        System.out.println("问题：" + question);

        //将问题解析成键值对存储到Map集合中
        params=new HashMap<String,ArrayList<String>>();
        querys=new HashMap<String,ArrayList<String>>();
        int pass = qs.questionParse(question,params,querys);//解析问题

        if(pass == -1) {
            sendAnswer(response,"问题不合法，无法回答！");
            return;
        }

        int num = qs.questionVerify(params);
        if(num ==-1) {
            sendAnswer(response,"问题解析错误，无法回答！");
            return;
        }

        try {
            results = qs.questionQuery(params, querys, num);//查询数据库
            ans = rs.getAnswer(querys, results, num);//组织答案
            sendAnswer(response,ans);
        } catch (QuestionQueryException e) {
            sendAnswer(response,e.getMessage());
        } catch (SQLException e) {
            sendAnswer(response,"数据库错误！");
            e.printStackTrace();
        }




    }

    private void sendAnswer(javax.servlet.http.HttpServletResponse response, String ans) throws IOException {
        response.setCharacterEncoding("utf-8");
        //response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();
        out.write(ans);
        out.flush();
        out.close();
    }

    public void destory() {

    }

}
