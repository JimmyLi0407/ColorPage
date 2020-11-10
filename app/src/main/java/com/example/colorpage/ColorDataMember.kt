package com.example.colorpage


class ColorDataMember {
    var id : String
    var title : String
    var url : String
    var thumbnailUrl : String

    constructor(id : String, title : String, url : String, thumbnailUrl : String) {
        this.id = id
        this.title = title
        this.url = url
        this.thumbnailUrl = thumbnailUrl
    }
}