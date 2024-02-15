export function createObserver() {
    const observer = new MutationObserver((mutations) => {
      mutations.forEach((mutation) => {
        if (mutation.addedNodes && mutation.addedNodes.length > 0) {
          for (let i = 0; i < mutation.addedNodes.length; i++) {
            // 추가된 노드가 .pac-container 클래스를 가지고 있으면 스타일 변경
            if (mutation.addedNodes[i].nodeType === Node.ELEMENT_NODE && mutation.addedNodes[i].classList.contains('pac-container')) {
              console.log(mutation.addedNodes[i])
              mutation.addedNodes[i].style.zIndex = '9999';
            }
          }
        }
      });
    });
  
    observer.observe(document.body, {
        childList: true
    });
  }