let xhr = new XMLHttpRequest();
let currentPno;
let currentId;





function plikecountHandler() {
		if (xhr.readyState === 4 && xhr.status === 200) {
		
		const postLike = JSON.parse(xhr.responseText);
		let postLikeCount = postLike[0].plikecount;

		document.querySelector('#plikecountView').innerHTML = '좋아요 ' + postLikeCount + '개';
    }
}





function init() {
	
	// 현재 글의 고유 번호 pno 값 가져오기
	const postNumber = window.localStorage.getItem('postNumber');
	const postNo = JSON.parse(postNumber);
	currentPno = postNo.pno;
	
	
	
	// 로그인되어 있는지 아닌지 로컬스토리지 존재 유무로 판단하기
	const memberId = window.localStorage.getItem('memberId');
	const member = JSON.parse(memberId);
	   

	
	document.querySelector('#plikecountUpdate').addEventListener('click', function() {

		if (member) {
			currentId = member.id;
			
			xhr.onreadystatechange = plikecountHandler;
			
		    let param = '?command=updateLike&pno=' + currentPno + '&id=' + currentId;
		    xhr.open('GET', '../ajaxController/toAjaxController.jsp' + param, true);
		    xhr.send();		
		}
		else {
		    alert('로그인이 필요한 서비스 입니다.');
		    window.location.href = '/finalProject/views/login';
		}
	});
}





window.addEventListener('load', init);