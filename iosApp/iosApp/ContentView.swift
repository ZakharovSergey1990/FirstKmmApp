import SwiftUI
import shared

struct ContentView: View {
	let greet = Greeting().greeting()
    
    @State var xxx = "Loading"

    func load(){

               let repo = UserRepositoryImpl.init(userDataSource: UserDataSourceImpl.init(driver: DriverFactory.init(context: NSObject.init())), testHttpApi: TestHttpApiImpl.init())
       xxx = "start get Users"
        repo.getAllUsers(completionHandler: {result, error in
            if let result = result{
                self.xxx = "\(result.count)"
            } else if let error = error{
                self.xxx = "Error"
            }
        })
   }
    var body: some View {
        Text(xxx).onAppear(){
            load()
        }
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
