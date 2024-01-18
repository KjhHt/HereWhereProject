<template>
    <div class="items-center bg-white flex flex-col max-sm:hidden">
      <div
        class="justify-center items-stretch shadow-sm bg-white self-stretch flex gap-5 p-5 max-md:max-w-full max-md:flex-wrap max-sm:hidden"
      >
        <div
          class="justify-center items-center bg-white flex aspect-square flex-col w-10 h-10"
        >
          <img
            loading="lazy"
            srcSet="https://cdn.builder.io/api/v1/image/assets/TEMP/a9d69564af3d2a7e36786d72f81d7924cdf6dd2401d924e5b0c3358c13916065?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=100 100w, https://cdn.builder.io/api/v1/image/assets/TEMP/a9d69564af3d2a7e36786d72f81d7924cdf6dd2401d924e5b0c3358c13916065?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=200 200w, https://cdn.builder.io/api/v1/image/assets/TEMP/a9d69564af3d2a7e36786d72f81d7924cdf6dd2401d924e5b0c3358c13916065?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=400 400w, https://cdn.builder.io/api/v1/image/assets/TEMP/a9d69564af3d2a7e36786d72f81d7924cdf6dd2401d924e5b0c3358c13916065?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=800 800w, https://cdn.builder.io/api/v1/image/assets/TEMP/a9d69564af3d2a7e36786d72f81d7924cdf6dd2401d924e5b0c3358c13916065?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=1200 1200w, https://cdn.builder.io/api/v1/image/assets/TEMP/a9d69564af3d2a7e36786d72f81d7924cdf6dd2401d924e5b0c3358c13916065?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=1600 1600w, https://cdn.builder.io/api/v1/image/assets/TEMP/a9d69564af3d2a7e36786d72f81d7924cdf6dd2401d924e5b0c3358c13916065?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=2000 2000w, https://cdn.builder.io/api/v1/image/assets/TEMP/a9d69564af3d2a7e36786d72f81d7924cdf6dd2401d924e5b0c3358c13916065?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;"
            class="aspect-square object-contain object-center w-full overflow-hidden"
          />
        </div>
        <div
          class="text-black text-3xl font-medium leading-9 self-center grow shrink basis-auto my-auto max-md:max-w-full"
        >
          위치 검색
        </div>
        <div
          class="justify-between items-stretch bg-white self-center flex gap-5 my-auto"
        >
          <div class="text-black text-base leading-6">위치검색</div>
          <div class="text-black text-base leading-6">게시판</div>
          <div class="text-black text-base leading-6">매칭</div>
          <div class="text-black text-base leading-6">캘린더</div>
        </div>
        <div
          class="justify-center items-center bg-white flex aspect-square flex-col w-10 h-10"
        >
          <img
            loading="lazy"
            srcSet="https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=100 100w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=200 200w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=400 400w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=800 800w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=1200 1200w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=1600 1600w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=2000 2000w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;"
            class="aspect-square object-contain object-center w-full overflow-hidden"
          />
        </div>
      </div>
    </div>
    <div class="flex">
      <div class="w-1/6">
        <button
          data-drawer-target="default-sidebar"
          data-drawer-toggle="default-sidebar"
          aria-controls="default-sidebar"
          type="button"
          class="inline-flex items-center p-2 mt-2 ms-3 text-sm text-gray-500 rounded-lg sm:hidden hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-gray-200 dark:text-gray-400 dark:hover:bg-gray-700 dark:focus:ring-gray-600"
        >
          <span class="sr-only">Open sidebar</span>
          <svg
            class="w-6 h-6"
            aria-hidden="true"
            fill="currentColor"
            viewBox="0 0 20 20"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              clip-rule="evenodd"
              fill-rule="evenodd"
              d="M2 4.75A.75.75 0 012.75 4h14.5a.75.75 0 010 1.5H2.75A.75.75 0 012 4.75zm0 10.5a.75.75 0 01.75-.75h7.5a.75.75 0 010 1.5h-7.5a.75.75 0 01-.75-.75zM2 10a.75.75 0 01.75-.75h14.5a.75.75 0 010 1.5H2.75A.75.75 0 012 10z"
            ></path>
          </svg>
        </button>
  
        <aside
          id="default-sidebar"
          class="fixed left-0 z-40 w-1/6 h-screen transition-transform -translate-x-full sm:translate-x-0"
          aria-label="Sidebar"
        >
          <div
            class="h-full px-3 py-4 overflow-y-auto bg-gray-50 dark:bg-gray-800"
          >
            <ul class="space-y-2 font-medium">
              <li>
                <a
                  href="#"
                  class="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700 group"
                >
                  <svg
                    class="w-5 h-5 text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white"
                    aria-hidden="true"
                    xmlns="http://www.w3.org/2000/svg"
                    fill="currentColor"
                    viewBox="0 0 22 21"
                  >
                    <path
                      d="M16.975 11H10V4.025a1 1 0 0 0-1.066-.998 8.5 8.5 0 1 0 9.039 9.039.999.999 0 0 0-1-1.066h.002Z"
                    />
                    <path
                      d="M12.5 0c-.157 0-.311.01-.565.027A1 1 0 0 0 11 1.02V10h8.975a1 1 0 0 0 1-.935c.013-.188.028-.374.028-.565A8.51 8.51 0 0 0 12.5 0Z"
                    />
                  </svg>
                  <span class="ms-3">Dashboard</span>
                </a>
              </li>
              <li>
                <a
                  href="#"
                  class="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700 group"
                >
                  <svg
                    class="flex-shrink-0 w-5 h-5 text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white"
                    aria-hidden="true"
                    xmlns="http://www.w3.org/2000/svg"
                    fill="currentColor"
                    viewBox="0 0 18 18"
                  >
                    <path
                      d="M6.143 0H1.857A1.857 1.857 0 0 0 0 1.857v4.286C0 7.169.831 8 1.857 8h4.286A1.857 1.857 0 0 0 8 6.143V1.857A1.857 1.857 0 0 0 6.143 0Zm10 0h-4.286A1.857 1.857 0 0 0 10 1.857v4.286C10 7.169 10.831 8 11.857 8h4.286A1.857 1.857 0 0 0 18 6.143V1.857A1.857 1.857 0 0 0 16.143 0Zm-10 10H1.857A1.857 1.857 0 0 0 0 11.857v4.286C0 17.169.831 18 1.857 18h4.286A1.857 1.857 0 0 0 8 16.143v-4.286A1.857 1.857 0 0 0 6.143 10Zm10 0h-4.286A1.857 1.857 0 0 0 10 11.857v4.286c0 1.026.831 1.857 1.857 1.857h4.286A1.857 1.857 0 0 0 18 16.143v-4.286A1.857 1.857 0 0 0 16.143 10Z"
                    />
                  </svg>
                  <span class="flex-1 ms-3 whitespace-nowrap">Kanban</span>
                  <span
                    class="inline-flex items-center justify-center px-2 ms-3 text-sm font-medium text-gray-800 bg-gray-100 rounded-full dark:bg-gray-700 dark:text-gray-300"
                    >Pro</span
                  >
                </a>
              </li>
              <li>
                <a
                  href="#"
                  class="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700 group"
                >
                  <svg
                    class="flex-shrink-0 w-5 h-5 text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white"
                    aria-hidden="true"
                    xmlns="http://www.w3.org/2000/svg"
                    fill="currentColor"
                    viewBox="0 0 20 20"
                  >
                    <path
                      d="m17.418 3.623-.018-.008a6.713 6.713 0 0 0-2.4-.569V2h1a1 1 0 1 0 0-2h-2a1 1 0 0 0-1 1v2H9.89A6.977 6.977 0 0 1 12 8v5h-2V8A5 5 0 1 0 0 8v6a1 1 0 0 0 1 1h8v4a1 1 0 0 0 1 1h2a1 1 0 0 0 1-1v-4h6a1 1 0 0 0 1-1V8a5 5 0 0 0-2.582-4.377ZM6 12H4a1 1 0 0 1 0-2h2a1 1 0 0 1 0 2Z"
                    />
                  </svg>
                  <span class="flex-1 ms-3 whitespace-nowrap">Inbox</span>
                  <span
                    class="inline-flex items-center justify-center w-3 h-3 p-3 ms-3 text-sm font-medium text-blue-800 bg-blue-100 rounded-full dark:bg-blue-900 dark:text-blue-300"
                    >3</span
                  >
                </a>
              </li>
              <li>
                <a
                  href="#"
                  class="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700 group"
                >
                  <svg
                    class="flex-shrink-0 w-5 h-5 text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white"
                    aria-hidden="true"
                    xmlns="http://www.w3.org/2000/svg"
                    fill="currentColor"
                    viewBox="0 0 20 18"
                  >
                    <path
                      d="M14 2a3.963 3.963 0 0 0-1.4.267 6.439 6.439 0 0 1-1.331 6.638A4 4 0 1 0 14 2Zm1 9h-1.264A6.957 6.957 0 0 1 15 15v2a2.97 2.97 0 0 1-.184 1H19a1 1 0 0 0 1-1v-1a5.006 5.006 0 0 0-5-5ZM6.5 9a4.5 4.5 0 1 0 0-9 4.5 4.5 0 0 0 0 9ZM8 10H5a5.006 5.006 0 0 0-5 5v2a1 1 0 0 0 1 1h11a1 1 0 0 0 1-1v-2a5.006 5.006 0 0 0-5-5Z"
                    />
                  </svg>
                  <span class="flex-1 ms-3 whitespace-nowrap">Users</span>
                </a>
              </li>
              <li>
                <a
                  href="#"
                  class="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700 group"
                >
                  <svg
                    class="flex-shrink-0 w-5 h-5 text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white"
                    aria-hidden="true"
                    xmlns="http://www.w3.org/2000/svg"
                    fill="currentColor"
                    viewBox="0 0 18 20"
                  >
                    <path
                      d="M17 5.923A1 1 0 0 0 16 5h-3V4a4 4 0 1 0-8 0v1H2a1 1 0 0 0-1 .923L.086 17.846A2 2 0 0 0 2.08 20h13.84a2 2 0 0 0 1.994-2.153L17 5.923ZM7 9a1 1 0 0 1-2 0V7h2v2Zm0-5a2 2 0 1 1 4 0v1H7V4Zm6 5a1 1 0 1 1-2 0V7h2v2Z"
                    />
                  </svg>
                  <span class="flex-1 ms-3 whitespace-nowrap">Products</span>
                </a>
              </li>
              <li>
                <a
                  href="#"
                  class="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700 group"
                >
                  <svg
                    class="flex-shrink-0 w-5 h-5 text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white"
                    aria-hidden="true"
                    xmlns="http://www.w3.org/2000/svg"
                    fill="none"
                    viewBox="0 0 18 16"
                  >
                    <path
                      stroke="currentColor"
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="M1 8h11m0 0L8 4m4 4-4 4m4-11h3a2 2 0 0 1 2 2v10a2 2 0 0 1-2 2h-3"
                    />
                  </svg>
                  <span class="flex-1 ms-3 whitespace-nowrap">Sign In</span>
                </a>
              </li>
              <li>
                <a
                  href="#"
                  class="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700 group"
                >
                  <svg
                    class="flex-shrink-0 w-5 h-5 text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white"
                    aria-hidden="true"
                    xmlns="http://www.w3.org/2000/svg"
                    fill="currentColor"
                    viewBox="0 0 20 20"
                  >
                    <path
                      d="M5 5V.13a2.96 2.96 0 0 0-1.293.749L.879 3.707A2.96 2.96 0 0 0 .13 5H5Z"
                    />
                    <path
                      d="M6.737 11.061a2.961 2.961 0 0 1 .81-1.515l6.117-6.116A4.839 4.839 0 0 1 16 2.141V2a1.97 1.97 0 0 0-1.933-2H7v5a2 2 0 0 1-2 2H0v11a1.969 1.969 0 0 0 1.933 2h12.134A1.97 1.97 0 0 0 16 18v-3.093l-1.546 1.546c-.413.413-.94.695-1.513.81l-3.4.679a2.947 2.947 0 0 1-1.85-.227 2.96 2.96 0 0 1-1.635-3.257l.681-3.397Z"
                    />
                    <path
                      d="M8.961 16a.93.93 0 0 0 .189-.019l3.4-.679a.961.961 0 0 0 .49-.263l6.118-6.117a2.884 2.884 0 0 0-4.079-4.078l-6.117 6.117a.96.96 0 0 0-.263.491l-.679 3.4A.961.961 0 0 0 8.961 16Zm7.477-9.8a.958.958 0 0 1 .68-.281.961.961 0 0 1 .682 1.644l-.315.315-1.36-1.36.313-.318Zm-5.911 5.911 4.236-4.236 1.359 1.359-4.236 4.237-1.7.339.341-1.699Z"
                    />
                  </svg>
                  <span class="flex-1 ms-3 whitespace-nowrap">Sign Up</span>
                </a>
              </li>
            </ul>
          </div>
        </aside>
      </div>
      <div class="w-4/6">
        <div
          class="justify-center items-center self-stretch flex w-full flex-col mt-7 px-16 py-12 max-md:max-w-full max-md:px-5 max-sm:hidden"
        >
          <div class="w-full max-w-[1100px] my-2.5 max-md:max-w-full">
            <div>
              <div
                class="text-black text-4xl font-bold leading-10 justify-center items-center self-stretch w-full my-7 py-2 max-md:max-w-full max-md:px-5"
              >
                추천 여행지
              </div>
              <div>
                <ul class="flex ml-5">
                  <div class="w-72">
                    <li>
                      <div>
                        <img
                          loading="lazy"
                          srcSet="https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=100 100w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=200 200w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=400 400w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=800 800w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=1200 1200w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=1600 1600w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=2000 2000w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;"
                          class="aspect-square object-contain object-center w-64 overflow-hidden"
                        />
                      </div>
                    </li>
                  </div>
                  <div class="w-72">
                    <li>
                      <div>
                        <img
                          loading="lazy"
                          srcSet="https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=100 100w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=200 200w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=400 400w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=800 800w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=1200 1200w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=1600 1600w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=2000 2000w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;"
                          class="aspect-square object-contain object-center w-64 overflow-hidden"
                        />
                      </div>
                    </li>
                  </div>
                  <div class="w-72">
                    <li>
                      <div>
                        <img
                          loading="lazy"
                          srcSet="https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=100 100w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=200 200w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=400 400w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=800 800w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=1200 1200w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=1600 1600w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=2000 2000w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;"
                          class="aspect-square object-contain object-center w-64 overflow-hidden"
                        />
                      </div>
                    </li>
                  </div>
                  <div class="w-72">
                    <li>
                      <div>
                        <img
                          loading="lazy"
                          srcSet="https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=100 100w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=200 200w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=400 400w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=800 800w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=1200 1200w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=1600 1600w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=2000 2000w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;"
                          class="aspect-square object-contain object-center w-64 overflow-hidden"
                        />
                      </div>
                    </li>
                  </div>
                </ul>
              </div>
            </div>
            <div>
              <div
                class="text-black text-4xl font-bold leading-10 justify-center items-center self-stretch w-full my-7 py-2 max-md:max-w-full max-md:px-5"
              >
                주변 여행지
              </div>
              <div>
                <ul class="flex ml-5">
                  <div class="w-72">
                    <li>
                      <div>
                        <img
                          loading="lazy"
                          srcSet="https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=100 100w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=200 200w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=400 400w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=800 800w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=1200 1200w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=1600 1600w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=2000 2000w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;"
                          class="aspect-square object-contain object-center w-64 overflow-hidden"
                        />
                      </div>
                    </li>
                  </div>
                  <div class="w-72">
                    <li>
                      <div>
                        <img
                          loading="lazy"
                          srcSet="https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=100 100w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=200 200w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=400 400w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=800 800w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=1200 1200w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=1600 1600w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=2000 2000w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;"
                          class="aspect-square object-contain object-center w-64 overflow-hidden"
                        />
                      </div>
                    </li>
                  </div>
                  <div class="w-72">
                    <li>
                      <div>
                        <img
                          loading="lazy"
                          srcSet="https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=100 100w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=200 200w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=400 400w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=800 800w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=1200 1200w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=1600 1600w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=2000 2000w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;"
                          class="aspect-square object-contain object-center w-64 overflow-hidden"
                        />
                      </div>
                    </li>
                  </div>
                  <div class="w-72">
                    <li>
                      <div>
                        <img
                          loading="lazy"
                          srcSet="https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=100 100w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=200 200w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=400 400w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=800 800w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=1200 1200w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=1600 1600w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;width=2000 2000w, https://cdn.builder.io/api/v1/image/assets/TEMP/a5060af3effcc3ed82a034d8e107050d345ee4806d99ff8d7ee363510d253d12?apiKey=b922ee5000c140b983d4b85ee9eb3746&amp;"
                          class="aspect-square object-contain object-center w-64 overflow-hidden"
                        />
                      </div>
                    </li>
                  </div>
                </ul>
              </div>
            </div>
            <div>
              <div
                class="text-black text-4xl font-bold leading-10 justify-center items-center self-stretch w-full my-7 py-2 max-md:max-w-full max-md:px-5"
              >
                지도에서 찾기 <span class="text-xl">(이미지)</span>
              </div>
              <div>
                <div
                  class="self-stretch bg-black bg-opacity-10 flex flex-col justify-center items-stretch w-full mb-10 rounded-md max-md:max-w-full max-md:mt-10"
                >
                  <GoogleMap
                    api-key="AIzaSyA16otojrK96bOUH8jhs4Fp4gC3Glt2xrY"
                    style="width: 100%; height: 500px"
                    :center="center"
                    :zoom="3"
                    language="kor"
                  >
                    <MarkerCluster>
                      <MapMarker
                        v-for="(location, i) in locations"
                        :options="{ position: location }"
                        :key="i"
                      >
                        <InfoWindow>
                          <div id="contet">
                            <div id="siteNotice"></div>
                            <h1 id="firstHeading" class="firstHeading">Uluru</h1>
                            <div id="bodyContent">
                              <p>
                                <b>Uluru</b>, also referred to as
                                <b>Ayers Rock</b>, is a large sandstone rock
                                formation in the southern part of the Northern
                                Territory, central Australia. It lies 335&#160;km
                                (208&#160;mi) south west of the nearest large
                                town, Alice Springs; 450&#160;km (280&#160;mi) by
                                road. Kata Tjuta and Uluru are the two major
                                features of the Uluru - Kata Tjuta National Park.
                                Uluru is sacred to the Pitjantjatjara and
                                Yankunytjatjara, the Aboriginal people of the
                                area. It has many springs, waterholes, rock caves
                                and ancient paintings. Uluru is listed as a World
                                Heritage Site.
                              </p>
                              <p>
                                Attribution: Uluru,
                                <a
                                  href="https://en.wikipedia.org/w/index.php?title=Uluru&oldid=297882194"
                                >
                                  https://en.wikipedia.org/w/index.php?title=Uluru</a
                                >
                                (last visited June 22, 2009).
                              </p>
                            </div>
                          </div>
                        </InfoWindow>
                      </MapMarker>
                    </MarkerCluster>
                  </GoogleMap>
                </div>
                <input
                  type="file"
                  class="block w-full text-sm text-slate-500 file:mr-4 file:py-2 file:px-7 file:rounded-full file:border-0 file:text-sm file:font-semibold file:bg-violet-50 file:text-violet-700 hover:file:bg-violet-100"
                />
              </div>
            </div>
            <div>
              <div
                class="text-black text-4xl font-bold leading-10 justify-center items-center self-stretch w-full my-7 py-2 max-md:max-w-full max-md:px-5"
              >
                여행지 정보
              </div>
              <div class="flex ml-5">
                <div class="w-1/6"><span>리뷰</span></div>
                <div class="w-1/6"><span>유튜브</span></div>
                <div class="w-1/6"><span>근처맛집</span></div>
                <div class="w-2/6"><span>근처볼거리</span></div>
              </div>
              <div>
                <ul>
                  <li class="w-1/5 inline-block">
                    <div
                      class="justify-center items-stretch bg-black bg-opacity-10 p-4 ml-5 mt-7"
                    >
                      <div
                        class="items-stretch flex justify-between gap-2 max-md:max-w-full max-md:flex-wrap"
                      >
                        <div
                          class="bg-black bg-opacity-10 flex w-8 shrink-0 h-8 flex-col rounded-[32px]"
                        ></div>
                        <div
                          class="self-center overflow-hidden text-black text-ellipsis text-sm font-medium leading-5 grow my-auto max-md:max-w-full"
                        >
                          User 1
                        </div>
                      </div>
                      <div
                        class="overflow-hidden text-black text-ellipsis text-base leading-6 mt-4 max-md:max-w-full"
                      >
                        Review 1
                      </div>
                    </div>
                  </li>
                  <li class="w-1/5 inline-block">
                    <div
                      class="justify-center items-stretch bg-black bg-opacity-10 p-4 ml-5 mt-7"
                    >
                      <div
                        class="items-stretch flex justify-between gap-2 max-md:max-w-full max-md:flex-wrap"
                      >
                        <div
                          class="bg-black bg-opacity-10 flex w-8 shrink-0 h-8 flex-col rounded-[32px]"
                        ></div>
                        <div
                          class="self-center overflow-hidden text-black text-ellipsis text-sm font-medium leading-5 grow my-auto max-md:max-w-full"
                        >
                          User 1
                        </div>
                      </div>
                      <div
                        class="overflow-hidden text-black text-ellipsis text-base leading-6 mt-4 max-md:max-w-full"
                      >
                        Review 1
                      </div>
                    </div>
                  </li>
                  <li class="w-1/5 inline-block">
                    <div
                      class="justify-center items-stretch bg-black bg-opacity-10 p-4 ml-5 mt-7"
                    >
                      <div
                        class="items-stretch flex justify-between gap-2 max-md:max-w-full max-md:flex-wrap"
                      >
                        <div
                          class="bg-black bg-opacity-10 flex w-8 shrink-0 h-8 flex-col rounded-[32px]"
                        ></div>
                        <div
                          class="self-center overflow-hidden text-black text-ellipsis text-sm font-medium leading-5 grow my-auto max-md:max-w-full"
                        >
                          User 1
                        </div>
                      </div>
                      <div
                        class="overflow-hidden text-black text-ellipsis text-base leading-6 mt-4 max-md:max-w-full"
                      >
                        Review 1
                      </div>
                    </div>
                  </li>
                  <li class="w-1/5 inline-block">
                    <div
                      class="justify-center items-stretch bg-black bg-opacity-10 p-4 ml-5 mt-7"
                    >
                      <div
                        class="items-stretch flex justify-between gap-2 max-md:max-w-full max-md:flex-wrap"
                      >
                        <div
                          class="bg-black bg-opacity-10 flex w-8 shrink-0 h-8 flex-col rounded-[32px]"
                        ></div>
                        <div
                          class="self-center overflow-hidden text-black text-ellipsis text-sm font-medium leading-5 grow my-auto max-md:max-w-full"
                        >
                          User 1
                        </div>
                      </div>
                      <div
                        class="overflow-hidden text-black text-ellipsis text-base leading-6 mt-4 max-md:max-w-full"
                      >
                        Review 1
                      </div>
                    </div>
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import { defineComponent } from "vue";
  import {
    GoogleMap,
    Marker as MapMarker,
    MarkerCluster,
    InfoWindow,
  } from "vue3-google-map";
  import { ref, computed } from "vue";
  
  export default defineComponent({
    components: { GoogleMap, MapMarker, MarkerCluster, InfoWindow },
    setup() {
      const center = { lat: -28.024, lng: 140.887 };
      const locations = [
        { lat: -31.56391, lng: 147.154312 },
        { lat: -33.718234, lng: 150.363181 },
        { lat: -33.727111, lng: 150.371124 },
        { lat: -33.848588, lng: 151.209834 },
        { lat: -33.851702, lng: 151.216968 },
        { lat: -34.671264, lng: 150.863657 },
        { lat: -35.304724, lng: 148.662905 },
        { lat: -36.817685, lng: 175.699196 },
        { lat: -36.828611, lng: 175.790222 },
        { lat: -37.75, lng: 145.116667 },
        { lat: -37.759859, lng: 145.128708 },
        { lat: -37.765015, lng: 145.133858 },
        { lat: -37.770104, lng: 145.143299 },
        { lat: -37.7737, lng: 145.145187 },
        { lat: -37.774785, lng: 145.137978 },
        { lat: -37.819616, lng: 144.968119 },
        { lat: -38.330766, lng: 144.695692 },
        { lat: -39.927193, lng: 175.053218 },
        { lat: -41.330162, lng: 174.865694 },
        { lat: -42.734358, lng: 147.439506 },
        { lat: -42.734358, lng: 147.501315 },
        { lat: -42.735258, lng: 147.438 },
        { lat: -43.999792, lng: 170.463352 },
      ];
  
      const items = ref(["item1", "item2", "item3", /* ... */ "item15"]); // 전체 항목
      let currentIndex = ref(0); // 현재 표시할 항목의 시작 인덱스
  
      const currentList = computed(() => {
        return items.value.slice(currentIndex.value, currentIndex.value + 4);
      });
  
      const next = () => {
        currentIndex.value += 4;
        if (currentIndex.value >= items.value.length) {
          currentIndex.value = 0;
        }
      };
      return { center, locations, currentList, next };
    },
  });
  </script>
  