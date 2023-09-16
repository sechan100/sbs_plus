/** @type {import('tailwindcss').Config} */
module.exports = {

// 실제 사용중인 클래스들만 컴파일
//    purge: {
//        enabled: true,
//        content: ["../templates/thymeleaf/**/*.html"],
//      },

// 모든 클래스 컴파일
  content: ["../templates/thymeleaf/**/*.html"],



  theme: {
    extend: {},
  },
  plugins: [],
}

