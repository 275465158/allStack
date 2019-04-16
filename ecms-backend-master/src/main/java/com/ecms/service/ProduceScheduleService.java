package com.ecms.service;

import com.ecms.bean.ProduceScheduleInfo;
import com.ecms.dal.entity.ProduceScheduleEntity;
import com.ecms.dal.entity.ScheduleDetailEntity;
import com.ecms.manager.BaseManager;
import com.ecms.manager.ProduceScheduleManager;
import com.ecms.manager.ScheduleDetailManager;
import com.ecms.web.filter.LoginFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ProduceScheduleService extends BaseService<ProduceScheduleEntity, ProduceScheduleInfo> {

	@Resource
	private ProduceScheduleManager produceScheduleManager;
	@Resource
    private ScheduleDetailManager scheduleDetailManager;

    @Override
    protected BaseManager<ProduceScheduleEntity> getManager() {
        return produceScheduleManager;
    }

    /**
     * 插入排班表，同时插入到明细表
     * @param produceScheduleInfo
     */
    @Transactional
    public void insertByInfo(ProduceScheduleInfo produceScheduleInfo){
        Calendar cal = Calendar.getInstance();
        //获取当前时间:年
        int year = cal.get(Calendar.YEAR);
        //获取当前时间:月
        int month = cal.get(Calendar.MONTH) + 1;
        //获取当前时间:天
        int day = cal.get(Calendar.DATE);

        String dayStr;
        //如果当前天数<10,在前面加0
        if(day<10){
            dayStr = "0"+day;
        } else {
            dayStr = String.valueOf(day);
        }

        String thisMonthStr;
        if(month<10){
            thisMonthStr = "0"+month;
        } else {
            thisMonthStr = String.valueOf(month);
        }

        Map<String,Object> getDateMap;
        List<Map<String,Object>> res = new ArrayList<>();

        //根据当前月份，计划本年度剩下的月份
        for(int i = month ; i < MAX_MONTH ; i++){
            if(i == month){
                getDateMap = getDates(year,i,day);
            }else{
                getDateMap = getDates(year,i,0);
            }
            res.add(getDateMap);
        }
        //先删除今天日期之后的数据，再重新插入新数据
        String scheduleDate = year+"-"+thisMonthStr+"-"+dayStr;
        scheduleDetailManager.deleteByScheduleDate(scheduleDate);

        double workHours = Double.valueOf(produceScheduleInfo.getWorkDayHours());
        int workWeekDay = produceScheduleInfo.getWorkWeekDay();
        List<String> weekList;
        List<String> datesList;
        List<String> weekNumList;
        for(int j = 0 ; j < res.size() ; j++){
            weekList = (List) res.get(j).get("dayOfWeekList");
            datesList = (List) res.get(j).get("datesStrList");
            weekNumList = (List)res.get(j).get("dayOfWeekListNum");
            String scheduleId;
            int weekNum;
            for(int m = 0;m<weekList.size();m++){
                ScheduleDetailEntity scheduleDetailEntity = new ScheduleDetailEntity();
                //月份标识，每个月生成一个标识，格式：yyyymm
                scheduleId = datesList.get(m).replace("-","").substring(0,6);
                scheduleDetailEntity.setScheduleDate(datesList.get(m));
                scheduleDetailEntity.setScheduleWeek(weekList.get(m));
                //工作日状态为1，休息日状态为2
                weekNum = Integer.parseInt(weekNumList.get(m));
                //原来list中0表示星期日，将0转为7便于计算
                if(weekNum == 0){
                    weekNum = SUNDAY_NUM;
                }
                if (workWeekDay<weekNum) {
                    scheduleDetailEntity.setWorkStatus(REST_DAY);
                    scheduleDetailEntity.setWorkHours(REST_WORK_HOURS);
                } else {
                    scheduleDetailEntity.setWorkStatus(WORK_DAY);
                    scheduleDetailEntity.setWorkHours(workHours);
                }
                scheduleDetailEntity.setCompanyId(LoginFilter.getCurrentUser().getCompanyId());
                scheduleDetailEntity.setScheduleYear(year);
                scheduleDetailEntity.setScheduleId(scheduleId);
                scheduleDetailEntity.setStatus(STATUS_DEFAULT);
                scheduleDetailManager.insertBy(scheduleDetailEntity);
            }
        }
    /*
        System.err.println("当期时间: " + cal.getTime());
        System.err.println("日期: " + day);
        System.err.println("月份: " + month);
        System.err.println("年份: " + year);
        // 星期日为一周的第一天输出为 1，星期一输出为 2，以此类推
        System.err.println("一周的第几天: " + dayOfWeek);
        System.err.println("一月中的第几天: " + dayOfMonth);
        System.err.println("一年的第几天: " + dayOfYear);
    */
    }

    /**
     * 获取当前月工作日
     * @param year 当前年
     * @param month 当前月
     * @return
     */
    private static Map<String,Object> getDates(int year, int month,int dayToday){
        Map<String,Object> res = new HashMap<String, Object>();
        List<Date> dates = new ArrayList<Date>();
        List<String> datesStrList = new ArrayList<String>();

        List<Date> datesWork = new ArrayList<Date>();
        /*//每月工作日
        List<String> datesWorkStrList = new ArrayList<String>();*/
        List<String> dayOfWeekList = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH,  month - 1);
        cal.set(Calendar.DATE, 1);

        while(cal.get(Calendar.YEAR) == year &&
                cal.get(Calendar.MONTH) < month){
            int day = cal.get(Calendar.DAY_OF_WEEK);
            if(!(day == Calendar.SUNDAY || day == Calendar.SATURDAY)){
                datesWork.add((Date)cal.getTime().clone());
            }
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)-1;
            dates.add((Date)cal.getTime().clone());
            cal.add(Calendar.DATE, 1);
            dayOfWeekList.add(dayOfWeek+"");
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        //dayToday != 0表示获取本月当前时间之后的日期
        for(int i = dayToday;i<dates.size();i++){
            datesStrList.add(formatter.format(dates.get(i)));
        }
        //获取本月当前时间之后的每天的星期值
        if(dayToday != 0){
            dayOfWeekList = dayOfWeekList.subList(dayToday,dayOfWeekList.size());
        }

        /*//获取每月工作日
        for(int j = 0;j<datesWork.size();j++){
            datesWorkStrList.add(formatter.format(datesWork.get(j)));
        }
        res.put("datesWorkStrList",datesWorkStrList);*/

        //每个月日期数据
        res.put("datesStrList",datesStrList);
        //每个月星期数据
        res.put("dayOfWeekList",getWorkStr(dayOfWeekList));
        res.put("dayOfWeekListNum",dayOfWeekList);
        return res;
    }

    /**
     * 将数字转成星期
     * @param dayOfWeekList 星期list
     * @return
     */
    private static List<String> getWorkStr(List<String> dayOfWeekList) {
        List<String> dayOfWeekStrList = new ArrayList<String>();
        for(int i = 0;i<dayOfWeekList.size();i++){
            if (MONDAY_NUMSTR.equals(dayOfWeekList.get(i))){
                dayOfWeekStrList.add("星期一");
            } else if(TUESDAY_NUMSTR.equals(dayOfWeekList.get(i))){
                dayOfWeekStrList.add("星期二");
            } else if(WEDNESDAY_NUMSTR.equals(dayOfWeekList.get(i))){
                dayOfWeekStrList.add("星期三");
            } else if(THURSDAY_NUMSTR.equals(dayOfWeekList.get(i))){
                dayOfWeekStrList.add("星期四");
            } else if(FRIDAY_NUMSTR.equals(dayOfWeekList.get(i))){
                dayOfWeekStrList.add("星期五");
            } else if(SATURDAY_NUMSTR.equals(dayOfWeekList.get(i))){
                dayOfWeekStrList.add("星期六");
            } else if(SUNDAY_NUMSTR.equals(dayOfWeekList.get(i))){
                dayOfWeekStrList.add("星期日");
            }
        }
        return dayOfWeekStrList;
    }


}
