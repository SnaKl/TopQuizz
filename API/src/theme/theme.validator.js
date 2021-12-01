import {body} from 'express-validator';

export const ThemeValidator = {
    bodyTitle = body('title')
    .notEmpty()
    .isAlphanumeric()
    .withMessage('Theme title is required'),

    optionalBodyDescription = body('description')
    .optional()
}


export function createTheme(){
    return [
        ThemeValidator.bodyTitle,
        ThemeValidator.optionalBodyDescription
    ];
}