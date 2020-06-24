package reference;

public class Phone {
	//1. 멤버 필드
		private String smartPhone;
		private String image;
		
		//2 매개변수 있는 생성자 
		public Phone(String smartPhone , String image) {
			this.smartPhone=smartPhone;
			this.image=image;
		}
		//3.getter setter 
		public String getSmartPhone() {
			return smartPhone;
		}

		public void setSmartPhone(String smartPhone) {
			this.smartPhone = smartPhone;
		}

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}
		//4. 멤버함수
		//5. 오버라이드
		@Override
		public String toString() {
			return "Phone [smartPhone=" + smartPhone + ", image=" + image + "]";
		}
		
		
}
