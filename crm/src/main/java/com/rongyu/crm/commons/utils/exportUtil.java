package com.rongyu.crm.commons.utils;

import com.rongyu.crm.workbench.domain.Activity;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class exportUtil {
    /**
     * 根据市场活动集合与响应对象导出市场活动
     * @param activityList
     * @param response
     * @throws IOException
     */
    public static void exportActivity(List<Activity> activityList, HttpServletResponse response) throws IOException {
        //将市场活动写入创建的Excel表中
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("activityList.xls");
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("ID");
        cell = row.createCell(1);
        cell.setCellValue("所有者");
        cell = row.createCell(2);
        cell.setCellValue("名称");
        cell = row.createCell(3);
        cell.setCellValue("开始日期");
        cell = row.createCell(4);
        cell.setCellValue("结束日期");
        cell = row.createCell(5);
        cell.setCellValue("成本");
        cell = row.createCell(6);
        cell.setCellValue("描述");
        cell = row.createCell(7);
        cell.setCellValue("创建时间");
        cell = row.createCell(8);
        cell.setCellValue("创建者");
        cell = row.createCell(9);
        cell.setCellValue("修改时间");
        cell = row.createCell(10);
        cell.setCellValue("修改者");
        Activity activity = null;
        if (activityList != null && activityList.size() > 0){
            for(int i = 0;i<activityList.size();i++){
                activity = activityList.get(i);
                row = sheet.createRow(i+1);
                cell = row.createCell(0);
                cell.setCellValue(activity.getId());
                cell = row.createCell(1);
                cell.setCellValue(activity.getOwner());
                cell = row.createCell(2);
                cell.setCellValue(activity.getName());
                cell = row.createCell(3);
                cell.setCellValue(activity.getStartDate());
                cell = row.createCell(4);
                cell.setCellValue(activity.getEndDate());
                cell = row.createCell(5);
                cell.setCellValue(activity.getCost());
                cell = row.createCell(6);
                cell.setCellValue(activity.getDescription());
                cell = row.createCell(7);
                cell.setCellValue(activity.getCreateTime());
                cell = row.createCell(8);
                cell.setCellValue(activity.getCreateBy());
                cell = row.createCell(9);
                cell.setCellValue(activity.getEditTime());
                cell = row.createCell(10);
                cell.setCellValue(activity.getEditBy());
            }
        }
        /*//创建一个文件输出流（未优化部分）
        OutputStream os = new FileOutputStream("D:\\java_note\\4-ssm\\(6)-ssmproject\\crmActivity\\activityList.xls");
        wb.write(os);
        //关闭流
        os.close();
        wb.close();
        //把生成的文件下载到客户端
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.addHeader("Content-Disposition","attachment;filename=activityList.xls");
        OutputStream out = response.getOutputStream();
        InputStream is = new FileInputStream("D:\\java_note\\4-ssm\\(6)-ssmproject\\crmActivity\\activityList.xls");
        byte[] bt = new byte[256];
        int readLength = 0;
        while ((readLength = is.read(bt)) != -1){
            out.write(bt,0,readLength);
        }
        is.close();
        out.flush();*/
        //把生成的文件下载到客户端（优化部分）
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.addHeader("Content-Disposition","attachment;filename=activityList.xls");
        OutputStream out = response.getOutputStream();
        wb.write(out);
        //对流进行关闭和刷新
        wb.close();
        out.flush();
    }
}
