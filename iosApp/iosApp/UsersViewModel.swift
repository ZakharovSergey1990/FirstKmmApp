//
//  UsersViewModel.swift
//  iosApp
//
//  Created by user209353 on 1/31/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared
import SwiftUI
class UserViewModel : ObservableObject {
    
    var repo: UserRepository? = nil
 
    @Published var users = [User]()
    
    init(){
        let ns = NSObject()
        MultiplatformSDK().doInit(context: ns)
  getUsers()
    }
    
    func getUsers(){
       repo = MultiplatformSDK().userRepository
        
        repo?.getAllUsers(completionHandler: {result, error in
            if let result = result{
                result.collect(collector: Observer(callback: { value in
                    self.users = (value as? [User])!
                }), completionHandler: {value, error in})
            } else if let error = error{
                print("Error")
            }
        })
}
    
    func deleteUser(id: Int64){
        repo?.deleteUser(id: id, completionHandler: {value, error in
            print(value)
        })
    }
}
