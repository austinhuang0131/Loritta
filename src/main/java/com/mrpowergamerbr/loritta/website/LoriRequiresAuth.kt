package com.mrpowergamerbr.loritta.website

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.TYPE, AnnotationTarget.CLASS, AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class LoriRequiresAuth(val value: LoriAuthLevel)