
const currencyCodes={
    "미국 달러":"USD",
    "중국 위안":"CNY",
    "일본 엔":"JPY",
    "유럽 유로":"EUR",
    "영국 파운드":"GBP",
    "캐나다 달러":"CAD",
    "스위스 프랑":"CHF",
    "호주 달러":"AUD",
    "뉴질랜드 달러":"NZD",
    "홍콩 달러":"HKD",
    "대만 달러":"TWD",
    "몽골 투그릭":"MNT",
    "카자흐스탄 텡게":"KZT",
    "태국 바트":"THB",
    "싱가포르 달러":"SGD",
    "인도네시아 루피아":"IDR",
    "말레이지아 링깃":"MYR",
    "필리핀 페소":"PHP",
    "베트남 동":"VND",
    "브루나이 달러":"BND",
    "인도 루피":"INR",
    "파키스탄 루피":"PKR",
    "방글라데시 타카":"BDT",
    "멕시코 페소":"MXN",
    "브라질 헤알":"BRL",
    "아르헨티나 페소":"ARS",
    "스웨덴 크로나":"SEK",
    "덴마크 크로나":"DKK",
    "노르웨이 크로나":"NOK",
    "러시아 루블":"RUR",
    "헝가리 포린트":"HUF",
    "폴란드 즈워티":"PLN",
    "체코 코루나":"CZK",
    "사우디아라비아 리얄":"SAR",
    "카타르 리얄":"QAR",
    "이스라엘 셰켈":"ILS",
    "요르단 디나르":"JOD",
    "쿠웨이트 디나르":"KWD",
    "바레인 디나르":"BHD",
    "아랍에미리트 디르함":"AED",
    "튀르키예 리라":"TRY",
    "남아프리카공화국 랜드":"ZAR",
    "이집트 파운드":"EGP"
}

// [국가명/통화코드] 형태의 문자열 배열로 변환
export const formattedCurrencyCodes = Object.entries(currencyCodes).map(([name, code]) => `${name}/${code}`);

export const chartOptions = {
    chart: {
      id: 'currency-chart',
      type: 'area',
      height: 350,
      zoom: {
        autoScaleYaxis: true
      }
    },
    xaxis: {
      type: 'datetime',
    },
    dataLabels: {
      enabled: false
    },
    markers: {
      size: 0,
      style: 'hollow',
    },
    tooltip: {
      x: {
        format: 'yy.MM.dd'
      }
    },
    stroke: {
      curve: 'smooth'
    },
    fill: {
      type: 'gradient',
      gradient: {
        shadeIntensity: 1,
        inverseColors: false,
        opacityFrom: 0.5,
        opacityTo: 0,
        stops: [0, 90, 100]
      },
    }
  };

export function markerChange(chartOptions,series){

  const lastIndex = series[0].data.length - 1;

  const markers= {
    discrete: [{
      seriesIndex: 0,
      dataPointIndex: lastIndex, // 마지막 데이터 포인트 인덱스
      fillColor: '#ff4560', // 마지막 데이터 포인트 색상
      strokeColor: '#fff',
      size: 8 // 마지막 데이터 포인트 마커 사이즈
    }],
  }
  
  // dataLabels 설정
  const dataLabels = {
    enabled: true,
    enabledOnSeries: [0], // 첫 번째 시리즈에만 적용
    formatter: function (val, opts) {
      // 마지막 데이터 포인트에만 라벨 표시
      if (opts.dataPointIndex === lastIndex) {
        return val.toFixed(2); // 소수점 두 자리까지 표시
      } else {
        return '';
      }
    },
    background: {
      enabled: true,
      borderRadius: 2,
      padding: 4,
      opacity: 0.9,
      borderWidth: 1,
      borderColor: '#FF4560',
      dropShadow: {
        enabled: true,
        top: 1,
        left: 1,
        blur: 1,
        opacity: 0.45,
      },
    },
    style: {
      fontSize: '12px',
      fontFamily: 'Helvetica, Arial, sans-serif',
      colors: ['#FF4560'],
    },
    offsetY: -10,
  };



  return { ...chartOptions, markers, dataLabels };
}



