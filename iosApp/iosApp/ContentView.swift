import SwiftUI
import shared

struct ContentView: View {
   @ObservedObject var vm = UserViewModel()

    var body: some View {
        VStack(alignment: .center){
            ForEach(vm.users, id: \.id){ user in
                Text(user.name).onTapGesture {
                    vm.deleteUser(id: user.id)
                }
            }
        }
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}


