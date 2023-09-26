package logic;

public enum UserType {
	
	Customer(0){
		@Override
		public String toString() {
			return "/gui/CustomerMainScreenFrame.fxml";
		}
	};
	
	UserType(final int i) {}
}