import SwiftUI
import shared

struct ContentView: View {
	let greet = Greeting().greeting()
    
    @State var xxx = "Loading"

    func load(){

        let userDataSource = UserDataSourceImpl(driver: DriverFactory.init(context: NSObject.init()))
        
               let repo = UserRepositoryImpl.init(userDataSource: userDataSource, testHttpApi: TestHttpApiImpl.init())
    
        
        
        userDataSource.getAllUsersAsFlow().collect(collector: Observer(callback: {value in
           
            let result = value as? [User]
        
            print(result)
        
            xxx = "size = \(result[])"
        }), completionHandler: {data, error in
            print(data)
        })
        
       xxx = "start get Users"
        repo.getAllUsers(completionHandler: {result, error in
            if let result = result{
                self.xxx = "kfejgblakejfgb"
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

typealias Collector = Kotlinx_coroutines_coreFlowCollector

class Observer: Collector{
    
    let callback:(Any?) -> Void
    
    init(callback: @escaping (Any?) -> Void){
        self.callback = callback
    }

    func emit(value: Any?, completionHandler: @escaping (KotlinUnit?, Error?) -> Void) {
        callback(value)
        completionHandler(KotlinUnit(), nil)
    }
}
