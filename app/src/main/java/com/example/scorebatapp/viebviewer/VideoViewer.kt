package com.example.scorebatapp.viebviewer

class VideoViewer(val embeddedFrameUrl: String) : WidgetProvider() {
    override val iFrame: String
        get() = embeddedFrameUrl
}