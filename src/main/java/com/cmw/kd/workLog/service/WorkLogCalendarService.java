package com.cmw.kd.workLog.service;

import com.cmw.kd.core.commmonEnum.Role;
import com.cmw.kd.core.utils.CommonUtils;
import com.cmw.kd.workLog.model.WorkLogCalendarDto;
import com.cmw.kd.workLog.model.WorkLogCalendarUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkLogCalendarService {
  private final WorkLogCalendarMapper workLogCalendarMapper;

  public List<WorkLogCalendarDto> selectWorkLogCalendarList(WorkLogCalendarDto workLogCalendarDto) {
    // calendar list 가져옴
    if(Objects.isNull(workLogCalendarDto) || StringUtils.isBlank(workLogCalendarDto.getCalMonth())){
      workLogCalendarDto = new WorkLogCalendarDto();
      workLogCalendarDto.setCalMonth(LocalDate.now().withDayOfMonth(1).toString().substring(0, 7));
    }

    List<WorkLogCalendarDto> workLogCalendarDtoList = workLogCalendarMapper.selectWorkLogCalendarList(workLogCalendarDto.toEntity());
    workLogCalendarDtoList.forEach(dto -> dto.setCalDayName(LocalDate.parse(dto.getCalDate()).getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN)));

    return workLogCalendarDtoList;
  }

  public List<WorkLogCalendarDto> selectWorkLogAndCalendarList(WorkLogCalendarDto workLogCalendarDto) {
    // calendar list 가져옴
    if(Objects.isNull(workLogCalendarDto) || StringUtils.isBlank(workLogCalendarDto.getCalMonth())){
      workLogCalendarDto = new WorkLogCalendarDto();
      workLogCalendarDto.setCalMonth(LocalDate.now().withDayOfMonth(1).toString().substring(0, 7));
    }

    // 만약 ROLE 이 STAFF 면 세션에서 loginId 정보를 꺼내서 조회에 이용 아니면 그냥 빈 값으로 조회
    if(CommonUtils.getSession().getAttribute("loginMemberRole").toString().equals(Role.STAFF.toString())) {
      workLogCalendarDto.setRegId(CommonUtils.getSession().getAttribute("loginId").toString());
    }

    List<WorkLogCalendarDto> workLogCalendarDtoList = workLogCalendarMapper.selectWorkLogAndCalendarList(workLogCalendarDto.toEntity());
    workLogCalendarDtoList.forEach(dto -> dto.setCalDayName(LocalDate.parse(dto.getCalDate()).getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREAN)));

    return workLogCalendarDtoList;
  }

  public String selectPrevWorkLogCalendarList(WorkLogCalendarDto workLogCalendarDto) {
    String calDateStr = null;
    if(!Objects.isNull(workLogCalendarDto)){
      calDateStr = workLogCalendarDto.getCalMonth();
    }

    LocalDate prevMonthDate = null;
    if (StringUtils.isBlank(calDateStr)) {
      prevMonthDate = LocalDate.now().withDayOfMonth(1).minusDays(1);
    } else {
      String[] calDateArr = calDateStr.split("-");
      prevMonthDate = LocalDate.of(Integer.parseInt(calDateArr[0]), Integer.parseInt(calDateArr[1]), 1).minusDays(1);
    }

    WorkLogCalendarDto newWorkLogCalendarDto = new WorkLogCalendarDto();
    String prevMonthValue = prevMonthDate.toString().substring(0, 7);
    newWorkLogCalendarDto.setCalMonth(prevMonthValue);

    boolean result = !workLogCalendarMapper.selectWorkLogCalendarList(newWorkLogCalendarDto.toEntity()).isEmpty();

    return result ? prevMonthValue : null;
  }

  public String selectNextWorkLogCalendarList(WorkLogCalendarDto workLogCalendarDto) {
    String calDateStr = null;
    if(!Objects.isNull(workLogCalendarDto)){
      calDateStr = workLogCalendarDto.getCalMonth();
    }

    LocalDate nextMonthDate = null;
    if (StringUtils.isBlank(calDateStr)) {
      nextMonthDate = LocalDate.now().withDayOfMonth(1).plusMonths(2).minusDays(1);
    } else {
      String[] calDateArr = calDateStr.split("-");
      nextMonthDate = LocalDate.of(Integer.parseInt(calDateArr[0]), Integer.parseInt(calDateArr[1]), 1).plusMonths(2).minusDays(1);
    }

    WorkLogCalendarDto newWorkLogCalendarDto = new WorkLogCalendarDto();
    String nextMonthValue = nextMonthDate.toString().substring(0, 7);
    newWorkLogCalendarDto.setCalMonth(nextMonthValue);

    boolean result = !workLogCalendarMapper.selectWorkLogCalendarList(newWorkLogCalendarDto.toEntity()).isEmpty();
    return result ? nextMonthValue : null;
  }

  public void insertWorkLogCalendar(WorkLogCalendarDto workLogCalendarDto) {
    workLogCalendarMapper.insertWorkLogCalendar(workLogCalendarDto.toEntity());
  }

  public boolean updateWorkLogCalendar(WorkLogCalendarUpdateDto workLogCalendarUpdateDto) throws NullPointerException {
    return workLogCalendarMapper.updateWorkLogCalendar(workLogCalendarUpdateDto.toEntity()) > 0;
  }

  public boolean manualInsertWorkLogCalendar(String calMonth) {
    // year, month 가 다 있는지 확인
    // year는 숫자 형태인지 확인 -> String 으로 변환한 뒤에도 4자리인지 확인
    // month는 1~13사이인지 확인 -> String 변환
    // LocalDate.of() 를 이용해 year, month, 1 로 첫번째 날을 생성후 변수 선언
    // 만들어진 변수를 procedureInsertWorkLogCalendar() 메서드의 인수로 삽입 및 호출
    String[] calDateArr = calMonth.split("-");
    LocalDate calDate = LocalDate.of(Integer.parseInt(calDateArr[0]), Integer.parseInt(calDateArr[1]), 1);

    // 생성하려고 하는 일자가 현재보다 미래의 시점이면 생성 불가
    if(calDate.isAfter(LocalDate.now())) {
      return false;
    }

    WorkLogCalendarDto workLogCalendarDto = new WorkLogCalendarDto();
    workLogCalendarDto.setCalMonth(calDate.withDayOfMonth(1).toString().substring(0, 7));

    // workLogCalendarList 가 존재하는지 확인
    List<WorkLogCalendarDto> workLogCalendarDtoList = selectWorkLogCalendarList(workLogCalendarDto);
    if (workLogCalendarDtoList.isEmpty()) {
      procedureInsertWorkLogCalendar(calDate);
      return true;
    }
    return false;
  }

  public void procedureInsertWorkLogCalendar(LocalDate localDate){
//    LocalDate firstDate = LocalDate.now().withDayOfMonth(1);
    LocalDate firstDate = localDate.withDayOfMonth(1);
    int daySize = firstDate.lengthOfMonth();

    WorkLogCalendarDto workLogCalendarDto = new WorkLogCalendarDto();

    for (int i = 0; i < daySize; i++) {
      LocalDate saveDate = firstDate.plusDays(i);

      workLogCalendarDto.setCalDate(saveDate.toString());
      workLogCalendarDto.setCalMonth(saveDate.toString().substring(0, 7));

      String active = saveDate.getDayOfWeek().getValue() < 6 ? "Y" : "N";
      workLogCalendarDto.setActive(active);

      insertWorkLogCalendar(workLogCalendarDto);
    }
  }
}
