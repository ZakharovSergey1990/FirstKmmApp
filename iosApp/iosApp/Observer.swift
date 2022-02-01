//
//  Observer.swift
//  iosApp
//
//  Created by user209353 on 2/1/22.
//  Copyright © 2022 orgName. All rights reserved.
//
import shared
import Foundation

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
