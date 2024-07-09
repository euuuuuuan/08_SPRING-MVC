package com.euuuuuuan.handlermethod;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

@Controller
@RequestMapping("/first/*")
public class FirstController {
    /*
     * 컨트롤러 핸들러 메소드의 반환 값을 void로 설정하면 요청 주소가 view의 이름이 된다.
     * => /first/regist 요청이 들어오면 /first/regist 뷰를 응답한다.
     * */
    // 페이지 이동용 핸들러 메소드
    @GetMapping("regist")
    public void regist() {
    }

    /*
     * WebRequest로 요청 파라미터 전달받기
     * 파라미터 선언부에 WebRequest 타입을 선언하면 해당 메소드 호출시 인자로 값을 전달해준다.
     * HttpServletRequest, HttpservletResponse / servletRequest, ServletResponse 가능하다.
     * */
    @PostMapping("regist")
    public String registMenu(Model model, WebRequest request) {
        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));
        int categoryCode = Integer.parseInt(request.getParameter("categoryCode"));

        System.out.println("name = " + name);
        System.out.println("price = " + price);
        System.out.println("categoryCode = " + categoryCode);

        String message = name + "을(를) 신규메뉴 목록의 " + categoryCode + "번 카테고리에 "
                + price + "원으로 등록 하셨습니다!";

        System.out.println("message = " + message);
        model.addAttribute("message", message);

        return "first/messagePrinter";
    }

    /*
     * @RequestParam으로 요청 파라미터 전달받기
     * 요청 파라미터를 매핑해 호출시 값을 넣어주는 어노테이션으로 매개변수 앞에 작성한다.
     * form의 name 속성값과 매개변수의 이름이 다른 경우 @RequestParam("이름") 설정하면 된다.
     *
     * 생략이 가능하다. 하지만 가독성을 위해서 써주는 게 좋다.
     * */

    @GetMapping("modify")
    public void modify() {
    }

    @PostMapping("modify")
    public String modifyMenuPrice(Model model, @RequestParam String modifyName,
                                  @RequestParam int modifyPrice) {
        String message = modifyName + "메뉴의 가격을 " + modifyPrice + "원으로 가격을 변경하였습니다.";
        System.out.println("message = " + message);

        model.addAttribute("message", message);

        return "first/messagePrinter";
    }
}
