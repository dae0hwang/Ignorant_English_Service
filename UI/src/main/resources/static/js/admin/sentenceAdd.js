addEventListener("click", () => {
  alert("Clicked!");
});


// addEventListener("load" )

// let uiUrl = [[${@environment.getProperty('url.ui')}]];
//
// // const selectBoxGetValue =  axios({
// //   url: 'http://'+apiUrl+'/api/delivery-service/statistic/general/member/month',
// //   method: 'get',
// // });
//
// console.log(apiUrl);
// console.log(uiUrl);

let apiUrl = [[${@environment.getProperty('url.api')}]];
console.log(apiUrl);